
var app = new Vue({
    el: '#app',
    data: {
        entity: {
            username: '',
            password: '',
            repass: '',
        },
    },
    created() {
        this._notify('Vue-notify', 'success')
    },
    methods: {
        _notify(message, type) {
            // app.$notify({
            //     message: message,
            //     type: type
            // })
        },
        check() {
            if (this.entity.username == '' || this.entity.username == null) {

                return false;
            }
            if (this.entity.password == '' || this.entity.password == null) {

                return false;
            }
            if (this.entity.password != this.entity.repass) {

                return false;
            }
            return true;
        },

        //注册
        register() {
            var flag = this.check_enrol();
            if (flag) {
                var form = {
                    username: this.entity_enrol.username,
                    password: this.entity_enrol.password
                }
                this.$http.post('/user/create', form).then(response => {

                })
            }
        }
    }

})