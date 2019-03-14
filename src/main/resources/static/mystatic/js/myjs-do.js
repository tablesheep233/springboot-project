$(document).ready(function () {
    //初始化表格样式
    $.extend($.fn.dataTable.defaults,{
        "info":false,
        "lengthChange":false,
        "paging":false,
        "searching": false,
        "ordering": false
    });

    $('#dataTable').DataTable();




    //按删除时给表单初始化action,并弹出模态框
    $(".deleteBtn").click(function () {
        $("#deleteForm").attr("action",$(this).attr("del_url"));
        $("#deleteModal").modal({
            keyboard:true
        });
    })

    //当确认删除时提交表单
    $("#yes").click(function () {
        $("#deleteForm").submit();
    })

    //取消删除时清空表单action
    $("#cancel").click(function () {
        $("#deleteForm").attr("action","");
    })

    //当查找时给分页按钮添加查找值
    if ($("#searchAurl").length>0) {
        var href = $(this).attr("href")+"&search="+$("#searchAurl").val();
        if ($("#msearch").length>0){
            href = href+"&major="+$("#msearch").val();
        }
        if ($("#csearch").length>0){
            href = href+"&clazz="+$("#csearch").val();
        }
        $(".aurl").click(function () {
            event.preventDefault();
            $(location).attr("href",href);
        })
    }

    //增删改操作完成模态框
    if ($("#tip").length>0){
        $("#tipModal").modal({
            keyboard:true
        })
    }

    /**
     * 学生投递简历时查询学生所有简历
     */
    $("#chooseRecruitment").click(function () {
        $("#chooseBody").empty();
        $.getJSON("/chooseresume",function (result) {
            $.each(result,function (i,obj) {
                $("#chooseBody").append("<div class='form-control'><input class='fa fa-square-o' type='radio' name='resume' value='"+obj.id+"'>"
                    + obj.rname + obj.intention+"</div>")
            })
        });
        $("#chooseModal").modal({
            keyboard:true
        })
    })

    /**
     * 投递简历
     */
    $("#choosebut").click(function () {
        var resumeId = $("input:radio[name='resume']:checked").val();
        var recruitmentId = $("#recruitmentId").val();
        $.ajax({
            type: "post",
            url: "/chooseresume",
            data: {"resumeId":resumeId,"recruitmentId":recruitmentId},
            success: function(data){
                $("#tipbody").html(data.tip);
                $("#tipModal").modal({
                    keyboard:true
                })
            }
        })
    })

    $(".showResume").click(function () {
        var url = "/applyResume/"+$(this).attr("value");
       $.ajax({
           type:"get",
           url:url,
           async:true,
           success:showApplyResume,
           dataType:"json"
       });
    })
    
    function showApplyResume(data) {
        $("#resume").html(data.rname);
        $("#name").html(data.name);
        $("#birth").html(data.birth);
        $("#tel").html(data.tel);
        $("#email").html(data.email);
        $("#gender").html(data.gender);
        $("#intention").html(data.intention);
        $("#education").html(data.education);
        $("#wages").html(data.wages);
        $("#introduce").text(data.introduce);
        $("#skill").text(data.skill);
        $("#experience").text(data.experience);
        $("#resumeModal").modal({
            keyboard:true
        })
    }

    $(".showRecruitment").click(function () {
        var url = "/getSr/"+$(this).attr("value");
        $.ajax({
            type:"get",
            url:url,
            async:true,
            success:showRecruitment,
            dataType:"json"
        });
    })

    function showRecruitment(data) {
        $("#job").val(data.job);
        $("#rwages").val(data.wages);
        $("#grade").val(data.grade);
        $("#area").val(data.area);
        $("#rtel").val(data.tel);
        $("#remail").val(data.email);
        $("#requirement").html(data.requirement);
        $("#srModal").modal({
            keyboard:true
        })
    }

    $(".upSr").click(function () {
        var url = "/upSr";
        var recruitmentId = $(this).attr("value");
        var status = $(this).parent().parent("tr").children("td").eq(1).children("select").val();
        var user;
        $.ajax({
            type:"post",
            url:url,
            async:false,
            data:{"recruitmentId":recruitmentId,"status":status},
            success: function (data){
                user = data.user;
                $("#tipbody").html("成功");
                $("#tipModal").modal({
                    keyboard:true
                })
            }
        });
        $(this).parent().parent("tr").children("td").eq(2).html(user);
    })

    $(".enterpriseBtn").click(function () {
        var url = "/getEnterprise/"+$(this).attr("value");
        $.ajax({
            type:"get",
            url:url,
            async:true,
            success:showEnterprise,
            dataType:"json"
        });
    })

    function showEnterprise(data) {
        $("#ename").html(data.name);
        $("#etel").html(data.tel);
        $("#mail").html(data.email);
        $("#address").html(data.address);
        $("#creditno").html(data.creditNo);
        $("#introduction").text(data.introduction);
        $("#enterpriseModal").modal({
            keyboard:true
        })
    }

    $(".upStatus").click(function () {
        var url = "/upStatus";
        var enterpriseId = $(this).attr("value");
        var status = $(this).parent().parent("tr").children("td").eq(1).children("select").val();
        var user;
        $.ajax({
            type:"post",
            url:url,
            async:false,
            data:{"enterpriseId":enterpriseId,"status":status},
            success: function (data){
                user = data.user;
                $("#tipbody").html("成功");
                $("#tipModal").modal({
                    keyboard:true
                })
            }
        });
        $(this).parent().parent("tr").children("td").eq(2).html(user);
    })


})