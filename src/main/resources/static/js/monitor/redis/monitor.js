Highcharts.setOptions({
    global: {
        useUTC: false
    }
});
let app = new Vue({
    el: '#app',
    data: {
        tree: [], //Menu菜单的Tree集合
        info: JSON.parse(window.localStorage.getItem("info")), //从localStorage中获取登录用户数据
        list: [],
        defaultActive: 'Redis监控',
        loading: true,
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
        _notify(message, type) {
            this.$message({
                message: message,
                type: type
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
         * 初始化
         */
        init() {
            this.loading = true;
            //获取Tree
            this.$http.get(api.common.tree(this.info.username)).then(response => {
                let $this = response.body;
                if ($this.code == 200) {
                    this.tree = $this.data;
                }
            })
            this.$http.get(api.monitor.redis.info).then(response => {
                this.list = response.body.data;
                this.loading = false;
            })
            var $this = this;
            $this.$http.get(api.monitor.redis.memory).then(response => {
                if (response.body.code == 200) {
                    Highcharts.chart('memory', {
                        chart: {
                            type: 'spline',
                            animation: Highcharts.svg,
                            marginRight: 10,
                            events: {
                                load: function () {
                                    var series = this.series[0];
                                    setInterval(function () {
                                        $this.$http.get(api.monitor.redis.memory).then(response => {
                                            var data = response.body.data;
                                            var x = data.create_time,
                                                y = data.memory / 1024;
                                            series.addPoint([ x, y ], true, true);
                                            if (response.body.code == 200) {
                                            } else {
                                                this._notify(response.body.msg, 'error');
                                            }
                                        })
                                    }, 3e3);
                                }
                            }
                        },
                        title: {
                            text: "Redis 内存实时占用情况",
                            style: {
                                "font-size": "1.2rem"
                            }
                        },
                        xAxis: {
                            type: 'datetime',
                            tickPixelInterval: 150
                        },
                        yAxis: {
                            title: {
                                text: "kb"
                            },
                            plotLines: [ {
                                value: 0,
                                width: 1,
                                color: "#808080"
                            } ]
                        },
                        tooltip: {
                            formatter: function () {
                                return '<b>' + this.series.name + '</b><br/>' +
                                    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                                    Highcharts.numberFormat(this.y, 2);
                            }
                        },
                        legend: {
                            enabled: false
                        },
                        series: [ {
                            name: "内存占用（kb）",
                            data: function() {
                                var data = [], time = new Date().getTime(), i;
                                for (i = -19; i <= 0; i++) {
                                    data.push({
                                        x: time + i * 1e3,
                                        y: Math.random() * (1e3 - 800) + 800
                                    });
                                }
                                return data;
                            }()
                        } ]
                    });
                } else {
                    this._notify(response.body.msg, 'error');
                }
            })

            this.$http.get(api.monitor.redis.dbsize).then(response => {
                if (response.body.code == 200) {
                    Highcharts.chart('dbsize', {
                        chart: {
                            type: "spline",
                            animation: Highcharts.svg,
                            marginRight: 10,
                            events: {
                                load: function() {
                                    var series = this.series[0];
                                    setInterval(function() {
                                        $this.$http.get(api.monitor.redis.dbsize).then(response => {
                                            if (response.body.code == 200) {
                                                var data = response.body.data;
                                                var x = data.create_time, y = data.dbsize;
                                                series.addPoint([x, y], true, true);
                                            } else {
                                                this._notify(response.body.msg, 'error')
                                            }
                                        })
                                    }, 3e3);
                                }
                            }
                        },
                        title: {
                            text: "Redis key的实时数量",
                            style: {
                                "font-size": "1.2rem"
                            }
                        },
                        xAxis: {
                            type: "datetime",
                            tickPixelInterval: 150
                        },
                        yAxis: {
                            title: {
                                text: ""
                            },
                            plotLines: [ {
                                value: 0,
                                width: 1,
                                color: "#808080"
                            } ]
                        },
                        tooltip: {
                            formatter: function() {
                                return "<b>" + this.series.name + "</b><br/>" + Highcharts.dateFormat("%Y-%m-%d %H:%M:%S", this.x) + "<br/>" + Highcharts.numberFormat(this.y, 2);
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        legend: {
                            enabled: false
                        },
                        exporting: {
                            enabled: false
                        },
                        series: [ {
                            name: "keys",
                            data: function() {
                                var data = [], time = new Date().getTime(), i;
                                for (i = -19; i <= 0; i++) {
                                    data.push({
                                        x: time + i * 1e3,
                                        y: 0
                                    });
                                }
                                return data;
                            }()
                        } ]
                    });
                } else {
                    this._notify(response.body.msg, 'error');
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