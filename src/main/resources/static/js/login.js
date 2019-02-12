//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

let api = {
    login: '/login',
    info: '/user/info',
};

let app = new Vue({
    el: '#app',
    data: {
        entity: {
            username: '',
            password: '',
            remember: false,
            code: '',
        },
    },
    created() {
        if (window.localStorage.getItem("info") != null) {
            //如果浏览器localStorage中储存了用户信息，就先清空浏览器localStorage
            window.localStorage.removeItem("info");
        }
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
        check() {
            if (this.entity.username == '' || this.entity.username == null) {
                this._notify('用户名不能为空', 'warning');
                return false;
            }
            if (this.entity.password == '' || this.entity.password == null) {
                this._notify('密码不能为空', 'warning');
                return false;
            }
            return true;
        },

        //登录
        login() {
            let flag = this.check();
            if (flag) {
                let form = {
                    username: this.entity.username,
                    password: this.entity.password,
                    remember: this.entity.remember
                }
                this.$http.post('/login', form).then(response => {
                    let $this = response.body;
                    if ($this.code == 200) {
                        console.log($this);
                        //获取UserInfo
                        this.$http.get(api.info).then(result => {
                            let _$this = result.body;
                            //将UserInfo数据存储到浏览器的localStorage中
                            window.localStorage.setItem("info", JSON.stringify(_$this.data));
                            if (_$this.code == 200 && window.localStorage.getItem("info") != null) {
                                window.location.href = "/";
                            } else {
                                this._notify('获取用户信息失败', "error")
                            }
                        });

                        //登录成功跳转到首页
                        // window.location.href = '/';
                    } else {
                        console.log(response);
                        this._notify($this.msg, 'error');
                    }
                })
            }
        },
    }

})