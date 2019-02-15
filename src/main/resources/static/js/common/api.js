let {body} = document;
let WIDTH = 1024;
let RATIO = 3;

//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

//前端API访问接口
let api = {
    login: '/login',
    info: '/user/info',

    //公共接口
    common: {
        tree(name) {
            return '/user/getMenus?username=' + name;
        },
    },

    //系统管理
    system: {
        user: {
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
        },
        role: {
            list(pageCode, pageSize) {
                return '/role/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            menuButtonTree: '/menu/menuButtonTree',
            findById(id) {
                return '/role/findById?id=' + id;
            },
            delete: '/role/delete',
            update: '/role/update',
            add: '/role/add',
            checkName(name, id) {
                return "/role/checkName?name=" + name + '&id=' + id;
            }
        },
        menu: {
            list(pageCode, pageSize) {
                return '/menu/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            menuButtonTree: '/menu/menuButtonTree',
            menuTree: '/menu/menuTree',
            findById(id) {
                return '/menu/findById?id=' + id;
            },
            delete: '/menu/delete',
            update: '/menu/update',
            add: '/menu/add',
            getAllUrl: '/menu/urlList',
            checkName(name, id) {
                return "/menu/checkName?name=" + name + '&id=' + id;
            }
        },
        dept: {
            list(pageCode, pageSize) {
                return '/dept/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            roleTree: '/dept/tree',
            findById(id) {
                return '/dept/findById?id=' + id;
            },
            delete: '/dept/delete',
            update: '/dept/update',
            add: '/dept/add',
            checkName(name, id) {
                return "/dept/checkName?name=" + name + '&id=' + id;
            }
        },
    },

    //系统监控
    monitor: {
        online: {
            list: "/online/list",
            forceLogout(id) {
                return "/online/forceLogout?id=" + id
            }
        }
    },

}