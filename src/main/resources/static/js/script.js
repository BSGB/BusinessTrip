var isPaySlide = false;
var isTransportSlide = false;
var isSleepSlide = false;
var isLocalSlide = false;
var isSpendsSlide = false;

$('#togglePaysArrow').on('click', function () {
    if (isPaySlide) {
        $('#payArrow').html('<i class="fas fa-caret-down"></i>');
        isPaySlide = false;
    } else {
        $('#payArrow').html('<i class="fas fa-caret-up"></i>');
        isPaySlide = true;
    }

    $('#togglePays').slideToggle(200);
});


$('#toggleTransportArrow').on('click', function () {
    if (isTransportSlide) {
        $('#transportArrow').html('<i class="fas fa-caret-down"></i>');
        isTransportSlide = false;
    } else {
        $('#transportArrow').html('<i class="fas fa-caret-up"></i>');
        isTransportSlide = true;
    }

    $('#toggleTransport').slideToggle(200);
});

$('#toggleSleepArrow').on('click', function () {
    if (isSleepSlide) {
        $('#sleepArrow').html('<i class="fas fa-caret-down"></i>');
        isSleepSlide = false;
    } else {
        $('#sleepArrow').html('<i class="fas fa-caret-up"></i>');
        isSleepSlide = true;
    }

    $('#toggleSleep').slideToggle(200);
});

$('#toggleLocalArrow').on('click', function () {
    if (isLocalSlide) {
        $('#localArrow').html('<i class="fas fa-caret-down"></i>');
        isLocalSlide = false;
    } else {
        $('#localArrow').html('<i class="fas fa-caret-up"></i>');
        isLocalSlide = true;
    }

    $('#toggleLocal').slideToggle(200);
});

var costID = 0;
var amountID = 0;


$("#addSpend").on('click', function () {
    costID += 1;
    amountID += 1;

    var template = '<div class="rem">';

    template += '<div class="form-group"><label for="cost' + costID + '">Rodzaj wydatku</label><input name="costs" type="text" id="cost' + costID + '" class="form-control"></div>';

    template += '<div class="form-group"><label for="amount' + amountID + '">Kwota</label><input name="amounts" type="text" name="amount' + amountID + '" id="amount' + amountID + '" class="form-control"></div>';

    template += '<span class="removeCost"><i class="fas fa-trash"></i></span>';

    template += '<hr>';

    template += '</div>';

    $("#toggleSpends").append(template);
});

$('#toggleSpendsArrow').on('click', function () {
    if (isSpendsSlide) {
        $('#spendsArrow').html('<i class="fas fa-caret-down"></i>');
        isSpendsSlide = false;
    } else {
        $('#spendsArrow').html('<i class="fas fa-caret-up"></i>');
        isSpendsSlide = true;
    }

    $('#toggleSpends').slideToggle(200);
});

$("#toggleSpends").on('click', '.removeCost', function () {
    $(this).parent().fadeOut(200, function () {
        $(this).remove();
    });
});

//walidacja

$("#pay").on("keyup", function(){
    validateNumber($(this));
});

$("#leaveDate").change(function (){
    validateDate();
});
$("#arriveDate").change(function(){
    validateDate();
});

$("#leaveTime").change(function () {
   if($(this).val() === "") {
       $(this).addClass("is-invalid");
       $(this).tipsy({fallback: "Godzina wyjazdu", gravity: "w", fade: true});
   } else {
       $(this).removeClass("is-invalid");
       // $(this).addClass("is-valid");
   }
});

$("#arriveTime").change(function () {
    if($(this).val() === "") {
        $(this).addClass("is-invalid");
        $(this).tipsy({fallback: "Godzina wyjazdu", gravity: "w", fade: true});
    } else {
        $(this).removeClass("is-invalid");
        // $(this).addClass("is-valid");
    }
});

$("#breakfast").on("keyup", function(){
    validateNumber($(this));
});
$("#dinner").on("keyup", function(){
    validateNumber($(this));
});
$("#supper").on("keyup", function(){
    validateNumber($(this));
});

$("#transType").on("keyup", function(){
    validateText($(this));
});

$("#ticketPrice").on("keyup", function(){
    validateNumber($(this));
});

$("#ovCcm").on("keyup", function(){
    validateNumber($(this));
});

$("#unCcm").on("keyup", function(){
    validateNumber($(this));
});

$("#motorcycle").on("keyup", function(){
    validateNumber($(this));
});

$("#motBicycle").on("keyup", function(){
    validateNumber($(this));
});

$("#lumpSum").on("keyup", function(){
    validateNumber($(this));
});

$("#sleepBill").on("keyup", function(){
    validateNumber($(this));
});

$("#pLumpSum").on("keyup", function(){
    validateNumber($(this));
});

$("#returnPay").on("keyup", function(){
    validateNumber($(this));
});

$("#advance").on("keyup", function(){
    validateNumber($(this));
});

$("form").on('keyup', 'input[name="costs"]', function () {
    validateText($(this));
});

$("form").on('keyup', 'input[name="amounts"]', function () {
    validateNumber($(this));
});


////

function validateNumber(e) {
    if(isNaN(e.val())){
        e.addClass("is-invalid");
        e.tipsy({fallback: "Wartość liczbowa", gravity: "w", fade: true});
    } else {
        e.removeClass("is-invalid");
        // e.addClass("is-valid");
    }
}

function validateText(e) {
    if(!isNaN(e.val()) && e.val() !== ""){
        e.addClass("is-invalid");
        e.tipsy({fallback: "Wartość tekstowa", gravity: "w", fade: true});
    } else if(isNaN(e.val()) || e.val() === ""){

        e.removeClass("is-invalid");
    }
}

function validateDate() {
    var leaveDate = $('#leaveDate');
    var arriveDate = $('#arriveDate');
    var lDate = new Date(leaveDate.val());
    var aDate = new Date(arriveDate.val());
    if(lDate > aDate || arriveDate.val() === "" || leaveDate.val() === ""){
        leaveDate.addClass("is-invalid");
        arriveDate.addClass("is-invalid");
        leaveDate.tipsy({fallback: "Data wyjazdu (nie może być większa niż data powrotu)", gravity: "w", fade: true});
        arriveDate.tipsy({fallback: "Data powrotu (nie może być mniejsza niż data wyjazdu)", gravity: "w", fade: true});
    } else {
        leaveDate.removeClass("is-invalid");
        arriveDate.removeClass("is-invalid");
    }
}