
$(document).ready(function() {
    loadHarvests();
    $('#add-button').click(function (event) {
        event.preventDefault();
    $.ajax({
        type: 'POST',
        url: 'harvested',
        data: JSON.stringify({
            type: $("#add-harvested-type").val(),
            name: $("#add-harvested-name").val(),
            planet: $("add-harvested-planet").val(),
            area: $("#add-harvested-area").val(),
            size: $("#add-harvested-size").val(),
            food: $("#add-harvested-food").val()

        }),
        headers: {
            'Accept' : 'application/json',
            'Content-Type' : 'application/json'
        },
        'dataype' : 'json'
    }).done(function(data, status){
        loadHarvests();
        $('#add-harvested-type').val('');
        $('#add-harvested-name').val('');
        $('#add-harvested-planet').val('');
        $('#add-harvested-area').val('');
        $('#add-harvested-size').val('');
        $('#add-harvested-food').val('');
       
    });

    });
});

//The harvested Object
var harvested = {
    type: "",
    name: "",
    planet: "",
    area: "",
    size: "",
    food: ""
};





// The function to process the list of harvested objects into the table.
function processHarvestedList(harvests) {

    var harvestedRows = $('harvestedRows');

    $.each(harvests, function (index, harvested) {
        //this adds all the parts of the table from the 'harvested' object
        var nameField = $("<td>");
        var typeField = $("<td>");
        var editField = $("<td>");
        var deleteField = $("<td>");
        var nameLink = $("<td>");

        // For the creation of the modeal and what is inside.
        nameLink.attr({
            'data-toggle': 'modal',
            'data-target': '#harvested-details-modal',
            'data-harvested-name': harvested.name
        });

        // Filling out the name part of the table
        nameLink.text(harvested.name);
        nameField.append(nameLink);

        // Making the table go to a certain modal for editing.
        var editLink = $("<a>");
        editLink.attr({
            'data-toggle': 'modal',
            'data-target': '#harvested-edit-modal',
            'data-harvested-name': harvested.name
        });
        //For deleting from the table
        var deleteLink = $("<a>");
        deleteLink.attr({
            'onclick': 'removeHarvested(' + harvested.name + ')'
        });

        deleteLink.text("Delete");
        deleteField.append(deleteLink);
        var harvestedRow = $("<tr>");
        harvestedRow.append(nameField);
        harvestedRow.append(typeField);
        harvestedRow.append(editField);
        harvestedRow.append(deleteField);
        harvestedRows.append(harvestedRow);

    });
}

function fillTable(harvestedList, status) {
    var harvestedRows = $('#harvestedRows');

    $.each(harvestedList, function (index, harvested) {

        //this adds all the parts of the table from the 'harvested' object
        var nameField = $("<td>");
        var typeField = $("<td>");
        var editField = $("<td>");
        var deleteField = $("<td>");
        var nameLink = $("<td>");

        // For the creation of the modal and what is inside.
        nameLink.attr({
            'data-toggle': 'modal',
            'data-target': '#harvested-details-modal',
            'data-harvested-name': harvested.name
        });

        // Filling out the name part of the table
        nameLink.text(harvested.name);
        nameField.append(nameLink);

        // Making the table go to a certain modal for editing.
        var editLink = $("<a>");
        editLink.attr({
            'data-toggle': 'modal',
            'data-target': '#harvested-edit-modal',
            'data-harvested-name': harvested.name
        });
        //For deleting from the table
        var deleteLink = $("<a>");
        deleteLink.attr({
            'onclick': 'removeHarvested(' + harvested.name + ')'
        });

        deleteLink.text("Delete");
        deleteField.append(deleteLink);
        var harvestedRow = $("<tr>");
        harvestedRow.append(nameField);
        harvestedRow.append(typeField);
        harvestedRow.append(editField);
        harvestedRow.append(deleteField);
        harvestedRows.append(harvestedRow);
    }); 





















}

 function loadHarvests() {
    var harvestedContents = $('#harvestedRows');
    // clearTable();
    $.ajax({
        type: 'GET',
        url: 'harvests',
        success: (function (data, status) {
            fillTable(data, status);
        })
    });

    
}

// function clearTable() {
//     $('#harvestedRows').empty();

// }