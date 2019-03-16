let {body} = document;
let WIDTH = 1024;
let RATIO = 3;

//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

//前端API访问接口
let api = {
    login: '/login',
    gifCode: '/gifCode?',
    info: '/system/user/info',

    //公共接口
    common: {
        updatePassword(password) {
            return '/system/user/updatePassword?password=' + password
        },
        tree(name) {
            return '/system/user/getMenus?username=' + name;
        },
    },

    //系统管理
    system: {
        user: {
            list(pageCode, pageSize) {
                return '/system/user/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            localUpload: '/storage/local/upload',
            qiniuUpload: '/storage/qiniu/upload',
            getUser(id) {
                return "/system/user/findById?id=" + id;
            },
            avatar: '/file/avatar.json',
            changeAvatar(url) {
                return "/system/user/changeAvatar?url=" + url;
            },
            roleList: '/system/role/list',
            deptTree: '/system/dept/tree',
            add: '/system/user/add',
            update: '/system/user/update',
            delete: '/system/user/delete',
            checkName(name, id) {
                return "/system/user/checkName?name=" + name + '&id=' + id;
            }
        },
        role: {
            list(pageCode, pageSize) {
                return '/system/role/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            menuButtonTree: '/system/menu/menuButtonTree',
            findById(id) {
                return '/system/role/findById?id=' + id;
            },
            delete: '/system/role/delete',
            update: '/system/role/update',
            add: '/system/role/add',
            checkName(name, id) {
                return "/system/role/checkName?name=" + name + '&id=' + id;
            }
        },
        menu: {
            list(pageCode, pageSize) {
                return '/system/menu/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            menuButtonTree: '/system/menu/menuButtonTree',
            menuTree: '/system/menu/menuTree',
            findById(id) {
                return '/system/menu/findById?id=' + id;
            },
            delete: '/system/menu/delete',
            update: '/system/menu/update',
            add: '/system/menu/add',
            getAllUrl: '/system/menu/urlList',
            checkName(name, id) {
                return "/system/menu/checkName?name=" + name + '&id=' + id;
            }
        },
        dept: {
            list(pageCode, pageSize) {
                return '/system/dept/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            roleTree: '/system/dept/tree',
            findById(id) {
                return '/system/dept/findById?id=' + id;
            },
            delete: '/system/dept/delete',
            update: '/system/dept/update',
            add: '/system/dept/add',
            checkName(name, id) {
                return "/system/dept/checkName?name=" + name + '&id=' + id;
            }
        },
    },

    //系统监控
    monitor: {
        online: {
            list: "/monitor/online/list",
            forceLogout(id) {
                return "/monitor/online/forceLogout?id=" + id
            }
        },
        loginlog: {
            list(pageCode, pageSize) {
                return '/monitor/loginlog/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            delete: '/monitor/loginlog/delete',
        },
        log: {
            list(pageCode, pageSize) {
                return '/monitor/log/list?pageCode=' + pageCode + '&pageSize=' + pageSize;
            },
            delete: '/monitor/log/delete',
        },
        redis: {
            memory: '/monitor/redis/memory',
            dbsize: '/monitor/redis/dbsize',
            info: '/monitor/redis/info',
        },
    },

    //对象储存
    storage: {
        qiniu: {
            list: '/storage/qiniu/list',
            domain: '/storage/qiniu',
            upload: '/storage/qiniu/upload',
            download() {
                return '/storgae/qiniu/download?name=' + name;
            },
            deleteOne(name) {
                return '/storage/qiniu/delete?name=' + name;
            },

            findOne(name) {
                return '/storage/qiniu/find?name=' + name
            },
            updateOne(oldname, newname) {
                return '/storage/qiniu/update?oldname=' + oldname + '&newname=' + newname;
            },
        },
    },

    //网络资源
    web: {
        weather: {
            citys: '/file/city.json',
            search(cityIds) {
                return '/web/weather/search?cityIds=' + cityIds;
            },
        },
        movie: {
            hot: '/web/movie/hot',
        },
    }

}