// Vue实例
let app = new Vue({
    el: '#app',
    data: {
        tree: [], //Menu菜单的Tree集合
        info: JSON.parse(window.localStorage.getItem("info")), //从localStorage中获取登录用户数据
        defaultActive: '首页',
        mobileStatus: false, //是否是移动端
        sidebarStatus: true, //侧边栏状态，true：打开，false：关闭
        sidebarFlag: ' openSidebar ', //侧边栏标志
        dialogVisible: false,
        newpass: {
            password: ''
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
    },
    mounted() {
        this.$refs.loader.style.display = 'none';
    },
    methods: {
        /**
         * 初始化
         */
        init() {
            //获取Tree
            this.$http.get(api.common.tree(this.info.username)).then(response => {
                let $this = response.body;
                if ($this.code == 200) {
                    this.tree = $this.data;
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
