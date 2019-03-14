var cityDom = document.getElementById("repCity");
var myCityChart = echarts.init(cityDom);
cityOption = null;
function fetchData(cb) {
    var session = null;
    var year = null;

    if (document.getElementById("session")!=null){
        session = document.getElementById("session").value;
    }
    if (document.getElementById("year")!=null){
        year = document.getElementById("year").value;
    }
    $.ajax({
        type: "post",
        url: "/city",
        data:{"year":year,"session":session},
        success: function(data){
            cb(data);
        }
    });
}

// 初始 option
cityOption = {
    title: {
        text: '就业地区分析'
    },
    tooltip: {},
    legend: {
        data:['地区']
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [{
        name: '地区',
        type: 'bar',
        data: []
    }]
};

myCityChart.showLoading();

fetchData(function (data) {
    myCityChart.hideLoading();
    myCityChart.setOption({
        xAxis: {
            data: data.city
        },
        series: [{
            // 根据名字对应到相应的系列
            name: '地区',
            data: data.data
        }]
    });
});
if (cityOption && typeof cityOption === "object") {
    myCityChart.setOption(cityOption, true);
}

function showCity() {
    myCityChart.showLoading();
    fetchData(function (data) {
        myCityChart.hideLoading();
        myCityChart.setOption({
            xAxis: {
                data: data.city
            },
            series: [{
                // 根据名字对应到相应的系列
                name: '地区',
                data: data.data
            }]
        });
    });
}



var industryDom = document.getElementById("repIndustry");
var myIndustryChart = echarts.init(industryDom);
industryOption = null;
function industryData(cb) {
    var session = null;
    var year = null;

    if (document.getElementById("isession")!=null){
        session = document.getElementById("isession").value;
    }
    if (document.getElementById("iyear")!=null){
        year = document.getElementById("iyear").value;
    }
    $.ajax({
        type: "post",
        url: "/industry",
        data:{"year":year,"session":session},
        success: function(data){
            cb(data);
        }
    });
}

// 初始 option
industryOption = {
    title: {
        text: '行业分析'
    },
    tooltip: {},
    legend: {
        data:['行业']
    },
    series: [{
        name: '行业',
        type: 'pie',
        radius: '55%',
        data: []
    }]
};

myIndustryChart.showLoading();

industryData(function (data) {
    myIndustryChart.hideLoading();
    myIndustryChart.setOption({
        series: [{
            // 根据名字对应到相应的系列
            name: '行业',
            data: data
        }]
    });
});
if (industryOption && typeof industryOption === "object") {
    myIndustryChart.setOption(industryOption, true);
}

function showIndustry() {
    myIndustryChart.showLoading();
    industryData(function (data) {
        myIndustryChart.hideLoading();
        myIndustryChart.setOption({
            series: [{
                // 根据名字对应到相应的系列
                name: '行业',
                data: data
            }]
        });
    });
}



var moneyDom = document.getElementById("repMoney");
var myMoneyChart = echarts.init(moneyDom);
moneyOption = null;
function moneyData(cb) {
    var session = null;
    var year = null;

    if (document.getElementById("msession")!=null){
        session = document.getElementById("msession").value;
    }
    if (document.getElementById("myear")!=null){
        year = document.getElementById("myear").value;
    }
    $.ajax({
        type: "post",
        url: "/money",
        data:{"year":year,"session":session},
        success: function(data){
            cb(data);
        }
    });
}

// 初始 option
moneyOption = {
    title: {
        text: '薪资分析'
    },
    tooltip: {},
    legend: {
        data:['薪资']
    },
    series: [{
        name: '薪资',
        type: 'pie',
        radius: '55%',
        roseType: 'angle',
        data: []
    }]
};

myMoneyChart.showLoading();

moneyData(function (data) {
    myMoneyChart.hideLoading();
    myMoneyChart.setOption({
        series: [{
            // 根据名字对应到相应的系列
            name: '薪资',
            data: data
        }]
    });
});
if (moneyOption && typeof moneyOption === "object") {
    myMoneyChart.setOption(moneyOption, true);
}

function showMoney() {
    myMoneyChart.showLoading();
    moneyData(function (data) {
        myMoneyChart.hideLoading();
        myMoneyChart.setOption({
            series: [{
                // 根据名字对应到相应的系列
                name: '薪资',
                data: data
            }]
        });
    });
}



var rrDom = document.getElementById("repRR");
var myRRChart = echarts.init(rrDom);
rrOption = null;
function rrData(cb) {
    var date = null;

    if (document.getElementById("date")!=null){
        date = document.getElementById("date").value;
    }
    $.ajax({
        type: "post",
        url: "/rr",
        data:{"date":date},
        success: function(data){
            cb(data);
        }
    });
}

// 初始 option
rrOption = {
    title: {
        text: '投递情况'
    },
    tooltip: {},
    legend: {
        data:['投递']
    },
    series: [{
        name: '投递',
        type: 'pie',
        radius: '55%',
        roseType: 'angle',
        data: []
    }]
};

myRRChart.showLoading();

rrData(function (data) {
    myRRChart.hideLoading();
    myRRChart.setOption({
        series: [{
            // 根据名字对应到相应的系列
            name: '投递',
            data: data
        }]
    });
});
if (rrOption && typeof rrOption === "object") {
    myRRChart.setOption(rrOption, true);
}

function showRR() {
    myRRChart.showLoading();
    rrData(function (data) {
        myRRChart.hideLoading();
        myRRChart.setOption({
            series: [{
                // 根据名字对应到相应的系列
                name: '投递',
                data: data
            }]
        });
    });
}
