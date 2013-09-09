var parsedJson;
var parsedViewType;

var minYear = 2099;
var maxYear = 0;

function mainFunc(){
    var json = parsedJson;
    var viewType = parsedViewType = $(".viewTypeClass input:checked").val();

    var datasets = getData(viewType, json);

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
    $.plot("#placeholder", [datasets[0]], options);
    
    $("#choices").find("input").click(plotAccordingToChoices);
    plotAccordingToChoices();
}

function firstRead() {
    var json = epam_json;

    var viewType = $(".viewTypeClass input:checked").val();

    parsedJson = json;
    parsedViewType = viewType;

    mainFunc();
}

function fillDiagramNames(datasets) {
    // insert checkboxes 
    var choiceContainer = $("#choices");
    $.each(datasets, function(key, val) {
        choiceContainer.append("<div><input type='checkbox' name='" + key + "' id='id" + key + "'></input>" +
                               "<label for='id" + key + "'>" + val.label + "</label></div>");
    });
    $("[type = 'checkbox']:first").prop("checked", true);
}

function saveYears(year) {
    if (maxYear < year) {
        maxYear = year;
    };
    if (minYear > year) {
        minYear = year;
    };
}

function readJson(json) {
    var datasets = new Object();
    // main loop
    for(yearName in json['EPAM']) {
        // loop with years
        var yearData = json['EPAM'][yearName];
        for (var townName in yearData) {
            var townData = yearData[townName];
            for (var j = 1; j < townData.length + 1; j++) {
                var year = parseInt(yearName.replace("Year",""));                
                // add year to datasets if not present
                if(!datasets.hasOwnProperty(townName)) {
                    datasets[townName] = {label: townName, data: []};
                }
                // push data for town for each year
                var xYear = year+0.25*(j-1);
                var yValue = townData[j-1];
                saveYears(xYear);
                datasets[townName].data.push([xYear, yValue]);
            };
        };
    };
    return datasets;
}

function getGlobalData(datasets) {
    var data1 = [];
    var sets = getEpamData(datasets);
    data1.push(sets['Epam']);  
    return data1;
}

function getCityData(datasets){
    var data1 = [];
    for(townName in datasets) {
        data1.push(datasets[townName]);
    };
    return data1;
}

function getData(type, json) {
    var datasets = readJson(json);
    var data1 = [];
    var extractData;
    switch(type) {
        case 'global':
            extractData = getGlobalData;
            break;
        case 'all':
            extractData = getCityData;
            break;
    };
    data1 = extractData(datasets);
    // hard-code colors to prevent them from shifting as towns are turned on/off
    var i = 0;
    $.each(datasets, function(key, val) {
        val.color = i;
        ++i;
    });
    $("#choices").empty();
    return data1;
}

function getEpamData(datasets){
    var sets = new Object();
    sets['Epam'] = {label:'Epam', data:[]};
    for (var i = minYear; i <= maxYear; i = i + 0.25) {
        sets['Epam']['data'].push([i, 0]);
    };
    for(townName in datasets) {
        for(index in datasets[townName]['data']) {
            var point = datasets[townName]['data'][index];
            for(jndex in sets['Epam']['data']) {
                var globPoint = sets['Epam']['data'][jndex];
                if(point[0] == globPoint[0]) {
                    sets['Epam']['data'][jndex][1] = globPoint[1] + point[1];
                }
            }
        }
    };
    return sets;
}

jQuery(document).ready(function($) {
    firstRead();    
});
$('[name = viewType]').on('change', mainFunc);