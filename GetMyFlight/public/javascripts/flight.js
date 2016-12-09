var SUCCESS = 'success';
var ERROR = 'error';

var serverErrorMessage = 'Oops, something wrong :(';

$(document).ready(function() {
    console.log("ghusa");
    $('#flightDataTable').DataTable( {
        "ajax": {
            "url": "/flight/list",
            "dataType": "json"
        },
         "columns": [
                    { "data": "source" },
                    { "data": "destination" },
                    { "data": "dateOfTravel"},
                    { "data": "carrier" },
					{ "data": "dateOfPriceFall" },
					{ "data": "predictedPrice" },
					{ "data": "latitude" },
					{ "data": "longitude" }
					
                   

                ]
    } );

    var tableflt = $('#flightDataTable').DataTable();
    
    $('#flightDataTable thead th').each (function(){
           
        	var title = $('#flightDataTable thead th').eq($(this).index()).text();
        	if(title==='Date of Travel'){
        	        $(this).html('<input type="date" placeholder="Search '+title+'"/> ');
        	}else{
        	    	$(this).html('<input type="text" placeholder="Search '+title+'"/> ');
        	}
        
        });
        
         tableflt.columns().eq(0).each(function( colIdx) {
        	$('input' , tableflt.column(colIdx).header() ).on( 'keyup change', function() {
        	    console.log($(this.value));
        		tableflt
        				.column(colIdx)
        				.search( this.value)
        				.draw();
        	});
        	
        });	
        

    // Delete flight event
  /*  $("body").on( 'click', '.remove-button', function () {
        var currentRow = $(this);
        var flightId = $(this).attr('id').trim();
         bootbox.confirm("Are you sure?", function(result) {
            if(result) {
                    $.ajax({
                     url: "/flight/delete",
                     type: "GET",
                     data: {fltId: flightId},
                     success:function(response){
                               if(response.status == SUCCESS) {
                                  showSuccessAlert(response.msg);
                                  tableflt.row(currentRow.parents('tr') ).remove().draw();
                              } else {
                                  showErrorAlert(serverErrorMessage);
                              }
                        },
                     error: function(){
                          showErrorAlert(serverErrorMessage);
                       }
                  });
            } else {
               //
              }
         });
    });*/
     
		

/*$('#fltModal').on('shown.bs.modal', function () {
  $('#fltForm').trigger("reset");
});*/

// Show success alert message
var showSuccessAlert = function (message) {
   	$.toaster({ priority : 'success', title : 'Success', message : message});
}

// Show error alert message
var showErrorAlert = function (message) {
    $.toaster({ priority : 'danger', title : 'Error', message : message});
}

// Convert form data in JSON format
$.fn.serializeObject = function() {
           var o = {};
           var a = this.serializeArray();
           $.each(a, function() {
                    if (o[this.name] !== undefined) {
                        if (!o[this.name].push) {
                            o[this.name] = [o[this.name]];
                        }
                        o[this.name].push(this.value || '');
                    } else {
                          if(this.name == 'id') {
                             o[this.name] = parseInt(this.value) || 0;
                          } else {
                         o[this.name] = this.value || '';
                         }
                    }
               });
            return JSON.stringify(o);
        };

		//**********this would be the search form*********************
		
// Handling form submission to search flight

    /*  $('#fltForm').on('submit', function(e){
         var formData = $("#fltForm").serializeObject();
         var fltTable = $('#flightDataTable').dataTable();
          e.preventDefault();
           $.ajax({
                url: "/flight/search",
                type: "POST",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: formData,
                success:function(response){
                   if(response.status == "success") {
                         $('#fltModal').modal('hide');
                         var flight = jQuery.parseJSON(formData);
                         flight['id'] = response.data['id'];
                         fltTable.fnAddData([flight]);
                         showSuccessAlert(response.msg);
                   } else {
                        $('#fltModal').modal('hide');
                        showErrorAlert(response.msg);
                   }
                },
                error: function(){
                    $('#fltModal').modal('hide');
                    showErrorAlert(serverErrorMessage);
                }

            });
            return false;
      });
    */
    
      
});
