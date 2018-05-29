$(function () {
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});

let comNameConditional = false;
let lDateConditional = false;
let aDateConditional = false;
let tableBody = $("#reportsTable tbody");

$("#reportsTable").on("click", "thead th", function () {
    let rows = $("#reportsTable tbody tr");
    switch(this.id){
        case "comNameSort":
            sortAndFill(comNameConditional, rows, tableBody, this.cellIndex);
            comNameConditional = !comNameConditional;
            break;

        case "lDateSort":
            sortAndFill(lDateConditional, rows, tableBody, this.cellIndex);
            lDateConditional = !lDateConditional;
            break;

        case "aDateSort":
            sortAndFill(aDateConditional, rows, tableBody, this.cellIndex);
            aDateConditional = !aDateConditional;
            break;
    }
});

function sortAndFill(conditional, rows, tableBody, cellIndex){
    if(!conditional){
        tableBody.html(sortAsc(rows, cellIndex));
    } else {
        tableBody.html(sortDesc(rows, cellIndex));
    }
}

function sortAsc(rows, tdNum) {

    for(let i = 0; i < rows.length - 1; i++){
        for(let j = 0; j < rows.length - i - 1; j++){
            if(rows[j].cells[tdNum].innerText > rows[j + 1].cells[tdNum].innerText) {
                let temp = rows[j];
                rows[j] = rows[j+1];
                rows[j+1] = temp;
            }
        }
    }

    return rows;
}

function sortDesc(rows, tdNum) {

    for(let i = 0; i < rows.length - 1; i++){
        for(let j = 0; j < rows.length - i - 1; j++){
            if(rows[j].cells[tdNum].innerText < rows[j + 1].cells[tdNum].innerText) {
                let temp = rows[j];
                rows[j] = rows[j+1];
                rows[j+1] = temp;
            }
        }
    }

    return rows;
}

// FILTER

$("#criteriaSelect").on("click", "option", function () {
   let option = this.attributes[0].nodeValue;
   let companies = $("#companySelector");
   let dates = $("#datePicker");

   switch(option){
       case "com":
           companies.removeClass("showAndHide");
           dates.addClass("showAndHide");
           break;
       case "date":
           companies.addClass("showAndHide");
           dates.removeClass("showAndHide");
           break;
       case "comAndDate":
           companies.removeClass("showAndHide");
           dates.removeClass("showAndHide");
           break;
   }
});

$(document).ready(function(){
    $("#filter-form").submit(function(e){
        e.preventDefault();

        filterAjaxSubmit();
    });
});

function filterAjaxSubmit(){
    let filterCrit = $("#criteriaSelect").val();
    let filterComName = $("#comNames").val();
    let filterLDate = $("#lDate").val();
    let filterADate = $("#aDate").val();
    if(!filterLDate) filterLDate = "2018-01-01";
    if(!filterADate) filterADate = "2018-01-01";

    let search = {
        filterCrit: filterCrit,
        filterComName: filterComName,
        filterLDate: filterLDate,
        filterADate: filterADate
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url : "/administration/reports/search",
        data: JSON.stringify(search),
        dataType: "json",
        success: function (data) {
            generateResultTable(data);
        },
        error: function (e) {
            //TODO
        },
        complete: function () {
            filterCrit = "";
            filterComName = "";
        }
    });
}

function generateResultTable(data){
    let table = $("#reportsTable tbody");
    let newContent = "";
    for(let i = 0; i < data.data.length; i++){
        for(let j = 0; j < data.data[i].reports.length; j++) {
        newContent += `<tr>`;
        newContent += `<td>${data.data[i].companyName}</td>`;
        newContent += `<td>${data.data[i].userLogin}</td>`;
        newContent += `<td>${data.data[i].reports[j].reportDescr}</td>`;
        newContent += `<td>${data.data[i].reports[j].reportLeaveTime}</td>`;
        newContent += `<td>${data.data[i].reports[j].reportArriveTime}</td>`;
        newContent += `<td>${data.data[i].reports[j].reportPayment}</td>`;
        newContent += `<td></td>`;
        newContent += `<td><a href="../calculator/download/pdf?reportId=${data.data[i].reports[j].id}"><i class="fas fa-file-pdf"></i> PDF</a></td>`;
        newContent += `<td><a href="../calculator/download/csv?reportId=${data.data[i].reports[j].id}"><i class="fas fa-file-excel"></i> CSV</a></td>`;
        newContent += `<td><a href="../calculator/result?reportId=${data.data[i].reports[j].id}">podgląd</a></td>`;
        newContent += `<td><a href="/administration/reports/delete?reportId=${data.data[i].reports[j].id}">usuń</a></td>`;
        newContent += `</tr>`;
        }
    };
    table.html(newContent);
}