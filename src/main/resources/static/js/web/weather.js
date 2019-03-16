// Vue实例
let app = new Vue({
    el: '#app',
    data: {
        tree: [], //Menu菜单的Tree集合
        info: JSON.parse(window.localStorage.getItem("info")), //从localStorage中获取登录用户数据
        defaultActive: '天气查询',
        cityIds: '',
        cityData: [],
        citys: [],
        weather: null,
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
        this.$http.get(api.web.weather.citys).then(response => {
            this.cityData = response.body;
        })
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

        //查询
        search() {
            this.cityIds = this.citys[0].areaid;
            this.$http.get(api.web.weather.search(this.cityIds)).then(response => {
                if (response.body.code == 200) {
                    this.weather = response.body.data;
                    var data = response.body.data;
                    var countyName = data.city;
                    var weathers = data.weathers;
                    var day_c = [];
                    var night_c = [];
                    var dateArr = [];
                    for (var i = 0; i < weathers.length; i++) {
                        if (i === weathers.length - 1) {
                            day_c.unshift(parseFloat(weathers[i].temp_day_c));
                            night_c.unshift(parseFloat(weathers[i].temp_night_c));
                            dateArr.unshift(weathers[i].date.split("-")[1] + "-" + weathers[i].date.split("-")[2]);
                        } else {
                            day_c.push(parseFloat(weathers[i].temp_day_c));
                            night_c.push(parseFloat(weathers[i].temp_night_c));
                            dateArr.push(weathers[i].date.split("-")[1] + "-" + weathers[i].date.split("-")[2]);
                        }
                    }
                    var weather3HoursDetailsInfos = data.weatherDetailsInfo.weather3HoursDetailsInfos;
                    var publishTime = data.weatherDetailsInfo.publishTime;
                    var timeArr = [];
                    var hours_c = [];
                    for (var i = 0; i < weather3HoursDetailsInfos.length; i++) {
                        var time = weather3HoursDetailsInfos[i].endTime.split(" ")[1].split(":");
                        hours_c.push(parseFloat(weather3HoursDetailsInfos[i].highestTemperature));
                        timeArr.push(time[0] + ":" + time[1]);
                    }
                    Highcharts.chart('weather', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: countyName + '七天气温图',
                            style: {
                                'font-size': '1.1rem',
                                'color': '#888'
                            }
                        },
                        xAxis: {
                            categories: dateArr
                        },
                        yAxis: {
                            title: {
                                text: '°C',
                                align: 'high',
                                rotation: 0
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        plotOptions: {
                            spline: {
                                dataLabels: {
                                    enabled: true
                                },
                                enableMouseTracking: true
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        series: [{
                            type: 'spline',
                            color: '#FFC77F',
                            name: '高温',
                            data: day_c
                        }, {
                            type: 'spline',
                            color: '#C5F0A4',
                            name: '低温',
                            data: night_c
                        }]
                    });
                    Highcharts.chart('dayWeather', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: countyName + '未来气温细节',
                            style: {
                                'font-size': '1.1rem',
                                'color': '#888'
                            }
                        },
                        xAxis: {
                            categories: timeArr
                        },
                        yAxis: {
                            title: {
                                text: '°C',
                                align: 'high',
                                rotation: 0
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        plotOptions: {
                            spline: {
                                dataLabels: {
                                    enabled: true
                                },
                                enableMouseTracking: true
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        series: [{
                            type: 'spline',
                            color: '#A1D9FF',
                            name: '气温',
                            data: hours_c
                        }]
                    });
                } else {
                    this._notify(response.body.msg, 'error')
                }
            })
        },

        remoteMethod(query) {
            if (query !== '') {
                setTimeout(() => {
                    this.citys = this.cityData.filter(item => {
                        if (item.countyname.indexOf(query) > -1) {
                            return item.areaid;
                        }
                    });
                }, 200);
            } else {
                this.citys = [];
            }
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