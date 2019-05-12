let app = new Vue({
    el: '#app',
    data() {
        var validateName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('名称不能为空'))
            }
            this.$http.get(api.system.user.checkName(value, this.form.id)).then(response => {
                if (response.body.code != 200) {
                    callback(new Error(response.body.msg))
                } else {
                    callback();
                }
            })
        }
        return {
            info: JSON.parse(window.localStorage.getItem("info")), //从localStorage中获取登录用户数据
            tree: '', //菜单Tree
            //form表单对象
            form: {
                username: '',
                password: '',
                deptId: [],
                createTime: '',
                avatar: '',
                status: null,
                phone: '',
                sex: '',
                description: '',
                roleIds: [],
            },
            qiniuUpload: api.system.user.qiniuUpload,
            localUpload: api.system.user.localUpload,
            deptTree: [], //部门Tree数据
            treeProps: {
                children: 'children',
                label: 'name'
            },
            checkForm: {
                username: [{ validator: validateName, trigger: 'blur' }]
            },
            //模态框状态标识
            formDialog: false,
            avatarDialog: false,
            avatarList: [],
            defaultActive: '首页',
            mobileStatus: false, //是否是移动端
            sidebarStatus: true, //侧边栏状态，true：打开，false：关闭
            sidebarFlag: ' openSidebar ', //侧边栏标志
            dialogVisible: false,
            newpass: {
                password: ''
            }
        }
    },
    created() {
        window.onload = function () {
            app.changeDiv();
        }
        window.onresize = function () {
            app.changeDiv();
        }
        this.init(); //初始化
    },
    mounted() {
        this.$refs.loader.style.display = 'none';
    },
    methods: {
        _notify(message, type) {
            this.$message({
                message: message,
                type: type
            })
        },
        /**
         * 初始化
         */
        init() {
            //获取Tree
            this.$http.get(api.common.tree(this.info.username)).then(response => {
                if (response.body.code == 200) {
                    this.tree = response.body.data;
                }
            })
        },
        //修改密码
        updatePass(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.$http.get(api.common.updatePassword(this.newpass.password)).then(response => {
                        window.location.href = '/logout';
                    })
                }
            })
        },

        //触发关闭按钮
        handleClose() {
            this.formDialog = false; //关闭模态框
            this.avatarDialog = false;
        },

        //触发修改头像按钮
        handleEditAvatar() {
            this.$http.get(api.system.user.avatar).then(response => {
                this.avatarList = response.body;
            });
            this.avatarDialog = true;
        },
        //修改头像
        changeAvatar(url) {
            this.form.avatar = url;
            this.info.avatar = url;
            window.localStorage.removeItem("info");
            window.localStorage.setItem("info", JSON.stringify(this.info));
            this.$http.get(api.system.user.changeAvatar(url)).then(response => {
                this.avatarDialog = false;
                if (response.body.code == 200) {
                    this._notify('更换头像成功', 'success')
                } else {
                    this._notify(response.body.msg, 'error')
                }
            })
        },

        //触发修改资料按钮
        handleEditInfo() {
            this.clearForm();
            //获取角色列表
            this.$http.post(api.system.user.roleList).then(response => {
                this.roleList = response.body.data.rows;
            })
            //获取部门Tree
            this.$http.get(api.system.user.deptTree).then(response => {
                this.deptTree = response.body.data;
            })
            this.$http.get(api.system.user.getUser(this.info.id)).then(response => {
                this.form = response.body.data;
                this.form.deptId = [response.body.data.deptId];
            })
            this.formDialog = true; //打开模态框
        },
        clearForm() {
            if (this.$refs.form != undefined) {
                this.$refs.form.resetFields(); //重置表单校验状态
            }
            this.form.username = ''
            this.form.password = ''
            this.form.deptId = []
            this.form.createTime = ''
            this.form.avatar = ''
            this.form.phone = ''
            this.form.sex = ''
            this.form.description = ''
            this.form.status = null
            this.form.roleIds = []
            this.form.id = null;
            this.form.roleId = null;
        },
        //保存、更新
        save(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.form.deptId = this.form.deptId[0];
                    this.formDialog = false;
                    //修改
                    this.$http.post(api.system.user.update, JSON.stringify(this.form)).then(response => {
                        if (response.body.code == 200) {
                            this._notify(response.body.msg, 'success')
                        } else {
                            this._notify(response.body.msg, 'error')
                        }
                        this.clearForm();
                    })
                    this.form.deptId = [];
                } else {
                    return false;
                }
            })
        },

        //Tree控件节点选中状态改变触发的事件
        checkChange(data, node, self) {
            if (node) {
                this.form.deptId = [data.id];
                this.$refs.tree.setCheckedKeys([data.id])
            } else {
                if (this.$refs.tree.getCheckedKeys().length == 0) {
                    this.form.deptId = [];
                }
            }
        },

        /**
         * 图片上传
         * @param res
         * @param file
         * @param fileList
         */
        //文件上传成功的钩子函数
        handleAvatarSuccess(res, file, fileList) {
            this._notify('图片上传成功', 'success');
            if (res.code == 200) {
                this.form.avatar = res.data.url;
                this.info.avatar = res.data.url;
                this.avatarDialog = false;
            }
        },
        //文件上传前的前的钩子函数
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isGIF = file.type === 'image/gif';
            const isPNG = file.type === 'image/png';
            const isBMP = file.type === 'image/bmp';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG && !isGIF && !isPNG && !isBMP) {
                this.$message.error('上传图片必须是JPG/GIF/PNG/BMP 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传图片大小不能超过 2MB!');
            }
            return (isJPG || isBMP || isGIF || isPNG) && isLt2M;
        },

        /**
         * 监听窗口改变UI样式（区别PC和Phone）
         */
        changeDiv() {
            let isMobile = this.isMobile();
            if (isMobile) {
                //手机访问
                this.sidebarFlag = ' hideSidebar mobile ';
                this.sidebarStatus = false;
                this.mobileStatus = true;
            } else {
                this.sidebarFlag = ' openSidebar';
                this.sidebarStatus = true;
                this.mobileStatus = false;
            }
        },
        isMobile() {
            let rect = body.getBoundingClientRect();
            return rect.width - RATIO < WIDTH
        },
        handleSidebar() {
            if (this.sidebarStatus) {
                this.sidebarFlag = ' hideSidebar ';
                this.sidebarStatus = false;

            } else {
                this.sidebarFlag = ' openSidebar ';
                this.sidebarStatus = true;
            }
            let isMobile = this.isMobile();
            if (isMobile) {
                this.sidebarFlag += ' mobile ';
                this.mobileStatus = true;
            }
        },
        //蒙版
        drawerClick() {
            this.sidebarStatus = false;
            this.sidebarFlag = ' hideSidebar mobile '
        }

    },
});