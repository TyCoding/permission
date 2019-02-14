//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

let api = {
    tree(name) {
        return '/user/getMenus?username=' + name;
    },
    list(pageCode, pageSize) {
        return '/user/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
    },
    localUpload: '/local/upload',
    getUser(id) {
        return "/user/findById?id=" + id;
    },
    avatar: '/file/avatar.json',
    changeAvatar(url) {
        return "/user/changeAvatar?url=" + url;
    },
    roleList: '/role/list',
    deptTree: '/dept/tree',
    add: '/user/add',
    update: '/user/update',
    delete: '/user/delete',
    checkName(name, id) {
        return "/user/checkName?name=" + name + '&id=' + id;
    }
};

// Vue实例
let app = new Vue({
    el: '#app',
    data() {
        var validateName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('名称不能为空'))
            }
            this.$http.get(api.checkName(value, this.form.id)).then(response => {
                if (response.body.code != 200) {
                    callback(new Error(response.body.msg))
                } else {
                    callback();
                }
            })
        }
        return {
            defaultActive: '用户管理',
            info: JSON.parse(window.localStorage.getItem("info")), //从localStorage中获取登录用户数据
            tree: '', //菜单Tree
            list: [], //用户列表数据
            searchEntity: {}, //查询实体类
            //分页选项
            pageConf: {
                //设置一些初始值(会被覆盖)
                pageCode: 1, //当前页
                pageSize: 6, //每页显示的记录数
                totalPage: 12, //总记录数
                pageOption: [6, 10, 20], //分页选项
            },
            //模态框状态标识
            dialogVisible: false,
            dialogTitle: '',
            avatarDialog: false,
            avatarList: [],
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
            localUpload: api.localUpload,
            roleList: [], //角色列表数据
            deptTree: [], //部门Tree数据
            treeProps: {
                children: 'children',
                label: 'name'
            },
            selectIds: [], //Table选中行ID

            checkForm: {
                username: [{ validator: validateName, trigger: 'blur' }]
            },

            mobileStatus: false, //是否是移动端
            sidebarStatus: true, //侧边栏状态，true：打开，false：关闭
            sidebarFlag: ' openSidebar ', //侧边栏标志
        }
    },
    created() {
        window.onload = function () {
            let $this = app;
            $this.changeDiv();
        }
        window.onresize = function () {
            let $this = app;
            $this.changeDiv();
        }

        this.init(); //初始化
        this.search(this.pageConf.pageCode, this.pageConf.pageSize);
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
            this.$http.get(api.tree(this.info.username)).then(response => {
                let $this = response.body;
                if ($this.code == 200) {
                    this.tree = $this.data;
                }
            })
        },

        //获取用户列表
        search(pageCode, pageSize) {
            this.$http.post(api.list(pageCode, pageSize), this.searchEntity).then(response => {
                let $this = response.body;
                this.list = $this.data.rows;
                this.pageConf.totalPage = $this.data.total;
            })
        },

        //pageSize改变时触发的函数
        handleSizeChange(val) {
            this.search(this.pageConf.pageCode, val);
        },
        //当前页改变时触发的函数
        handleCurrentChange(val) {
            this.pageConf.pageCode = val; //为了保证刷新列表后页面还是在当前页，而不是跳转到第一页
            this.search(val, this.pageConf.pageSize);
        },

        //触发关闭按钮
        handleClose() {
            this.dialogVisible = false; //关闭模态框
        },

        //触发修改头像按钮
        handleEditAvatar() {
            this.$http.get(api.avatar).then(response => {
                this.avatarList = response.body;
            });
            this.avatarDialog = true;
        },
        //修改头像
        changeAvatar(url) {
            this.form.avatar = url;
            this.avatarDialog = false;
        },

        //触发保存按钮：添加、更新
        handleSave(id) {
            this.clearForm();
            //获取角色列表
            this.$http.get(api.roleList).then(response => {
                this.roleList = response.body.data.rows;
            })
            //获取部门Tree
            this.$http.get(api.deptTree).then(response => {
                this.deptTree = response.body.data;
            })
            if (id == null) {
                this.dialogTitle = '新增用户'
            } else {
                this.dialogTitle = '修改用户'
                this.$http.get(api.getUser(id)).then(response => {
                    this.form = response.body.data;
                    if (response.body.data.deptId == null) {
                        this.form.deptId = []
                    } else {
                        this.form.deptId = [response.body.data.deptId];
                    }
                })
            }
            this.dialogVisible = true; //打开模态框
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
                    this.dialogVisible = false;
                    if (this.form.id == null || this.form.id == 0) {
                        this.$http.post(api.add, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    } else {
                        //修改
                        this.$http.post(api.update, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                if (this.form.id == this.info.id) {
                                    window.location.href = "/logout"
                                }
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    }
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
                this.$refs.tree.setCheckedKeys(this.form.deptId)
            } else {
                this.form.deptId = [];
            }
        },

        //Table选中触发事件
        selectChange(val) {
            this.selectIds = []
            val.forEach(row => {
                this.selectIds.push(row.id)
            })
        },

        //触发删除按钮
        handleDelete(id) {
            if (id != undefined) {
                this.selectIds = [id];
            }
            this.$confirm('你确定永久删除此账户？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http.post(api.delete, JSON.stringify(this.selectIds)).then(response => {
                    if (response.body.code == 200) {
                        this._notify('删除成功', 'success')
                    } else {
                        this._notify(response.body.msg, 'error')
                    }
                    this.$refs.table.clearSelection();
                    this.selectIds = [];
                    this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                })
            }).catch(() => {
                this._notify('已取消删除', 'info')
            });
        },

        //触发导出按钮
        handleExcel() {

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

let {body} = document;
let WIDTH = 1024;
let RATIO = 3;

