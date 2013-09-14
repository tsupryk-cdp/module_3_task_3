var receivedData;
var dataType;

function mainFunc(datasets){

    if (receivedData == 'undefined') {
        receivedData = datasets;
    }

    if (datasets['error'] == "Error") {
        alert("Some error occured on server.")
        return;
    }

    fillDiagramNames(datasets);

    var options = { legend:{noColumns: 2, position: "nw", container: $("#legend")},
                    yaxis: {min: 0},
                    xaxis: {tickDecimals: 0}
                  };
    
    function plotAccordingToChoices() {
        var data = [];
        $("#choices").find("input:checked").each(function () {
            var key = $(this).attr("name");
            if (key && datasets[key]) {
                data.push(datasets[key]);
            }
        });
        if (data.length > 0) {
            $.plot("#placeholder", data, options);
        }
    }

    // draw diagram
    $.plot("#placeholder", [datasets], options);
    
    $("#choices").find("input").click(plotAccordingToChoices);
    plotAccordingToChoices();
}

function fillDiagramNames(datasets) {
    $("#choices").empty();
    // insert checkboxes 
    var choiceContainer = $("#choices");
    if (datasets instanceof Array) {
        $.each(datasets, function(key, val) {
            choiceContainer.append("<div><input type='checkbox' name='" + key + "' id='id" + key + "'></input>" +
                                   "<label for='id" + key + "'>" + val.label + "</label></div>");
        });
    } else {
        choiceContainer.append("<div><input type='checkbox' name='" + datasets['color'] + "' id='id" + datasets['color'] + "'></input>" +
            "<label for='id" + datasets['color'] + "'>" + datasets['label'] + "</label></div>");
    }
    $("[type = 'checkbox']:first").prop("checked", true);
}

jQuery(document).ready(function($) {
    dataType = $(".viewTypeClass input:checked").val();
    performRequest(dataType);
});

function performRequest(type){
    $.get(
        "/module_3_task_3/json/" + type,
        {},
        mainFunc
    );
}

$('[name = viewType]').on('change', function(){
    dataType = $(".viewTypeClass input:checked").val();
    performRequest(dataType);
});