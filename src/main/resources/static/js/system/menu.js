let app = new Vue({
    el: '#app',
    data() {
        var validateName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('名称不能为空'))
            }
            this.$http.get(api.system.menu.checkName(value, this.form.id)).then(response => {
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
            //form表单对象
            form: {
                id: '',
                name: '',
                type: 'menu',
                perms: "",
                url: '',
                parentId: [],
                pid: [],
                icon: '',
            },
            menuTree: [], //菜单Tree
            menuButtonTree: [], //菜单按钮Tree
            treeProps: {
                children: 'children',
                label: 'name'
            },
            selectIds: [], //Table选中行ID
            checkForm: {
                name: [{ validator: validateName, trigger: 'blur' }]
            },
            urlList: [],
            defaultActive: '菜单管理',
            loading: true,
            mobileStatus: false, //是否是移动端
            sidebarStatus: true, //侧边栏状态，true：打开，false：关闭
            sidebarFlag: ' openSidebar ', //侧边栏标志
            newpass: {
                password: ''
            }
        }
    },
    created() {
        window.onload = function() {
            app.changeDiv();
        }
        window.onresize = function() {
            app.changeDiv();
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
            this.$http.get(api.common.tree(this.info.username)).then(response => {
                if (response.body.code == 200) {
                    this.tree = response.body.data;
                }
            })
            //获取Menu列表
            this.$http.get(api.system.menu.menuButtonTree).then(response => {
                this.menuButtonTree = response.body.data;
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

        //获取菜单列表
        search(pageCode, pageSize) {
            this.loading = true;
            this.$http.post(api.system.menu.list(pageCode, pageSize), this.searchEntity).then(response => {
                let $this = response.body;
                if ($this.code == 200) {
                    this.list = $this.data.rows;
                    this.pageConf.totalPage = $this.data.total;
                }
                this.loading = false;
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

        //触发保存按钮：添加、更新
        handleSave(id) {
            this.clearForm();
            //获取Menu列表
            this.$http.get(api.system.menu.menuTree).then(response => {
                this.menuTree = response.body.data;
            })
            //获取URL列表
            this.$http.get(api.system.menu.getAllUrl).then(response => {
                this.urlList = response.body.data;
            })
            if (id == null) {
                this.dialogTitle = '新增菜单/按钮'
            } else {
                this.dialogTitle = '修改菜单/按钮'
                this.$http.get(api.system.menu.findById(id)).then(response => {
                    this.form = response.body.data;
                    this.form.pid = [response.body.data.parentId]
                })
            }
            this.dialogVisible = true;
        },
        clearForm() {
            if (this.$refs.form != undefined) {
                this.$refs.form.resetFields();
            }
            this.form.id = ''
            this.form.name = ''
            this.form.icon = ''
            this.form.url = ''
            this.form.description = ''
            this.form.parentId = []
        },
        //保存
        save(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    this.dialogVisible = false;
                    this.form.parentId = this.form.pid[0];
                    if (this.form.id == null || this.form.id == 0) {
                        //添加
                        this.$http.post(api.system.menu.add, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.init();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    } else {
                        //修改
                        this.$http.post(api.system.menu.update, JSON.stringify(this.form)).then(response => {
                            if (response.body.code == 200) {
                                this._notify(response.body.msg, 'success')
                            } else {
                                this._notify(response.body.msg, 'error')
                            }
                            this.clearForm();
                            this.init();
                            this.search(this.pageConf.pageCode, this.pageConf.pageSize)
                        })
                    }
                } else {
                    return false;
                }
            })

        },

        //Tree控件节点选中状态改变触发的事件
        checkChange(data, node, self) {
            if (node) {
                this.form.pid = [data.id];
                this.$refs.tree.setCheckedKeys(this.form.pid)
            } else {
                if (this.$refs.tree.getCheckedKeys().length == 0) {
                    this.form.pid = [];
                }
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
            this.$confirm('你确定永久删除此菜单/按钮？, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$http.post(api.system.menu.delete, JSON.stringify(this.selectIds)).then(response => {
                    if (response.body.code == 200) {
                        this._notify('删除成功', 'success')
                    } else {
                        this._notify(response.body.msg, 'error')
                    }
                    this.init();
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