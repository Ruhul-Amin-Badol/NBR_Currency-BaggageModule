let addcurrencyData = [];

function populateCurrencyTable() {
  console.log(addData)
  const newRow1 = $("<tr>");
  // Get a reference to the table body
  const tbody = $("#currencyajaxtable tbody");
  // Clear the table body before populating it again
  tbody.empty();
  let totalAmount = 0.0;
  
  addcurrencyData.forEach(function (item) {
    // Create a new table row for each item
    const newRow = $("<tr>");
    // Populate the table cells in the new row with data from the item
    newRow.append(`<td> ${item.currencyName}</td>`); // Use the correct field names
    newRow.append(`<td> ${item.currencyNoteType}</td>`); // Use the correct field names
    newRow.append(`<td> ${item.numberOfNote}</td>`); // Use the correct field names
    newRow.append(`<td  >${item.currencyAmount}</td>`);

    newRow.append(
      `<td  class="text-center text-danger"><button  onClick="EditCurrencyProduct(${item.id})" type="button" class="btn btn-primary" id="addButton">Edit</button></td>`
    );
    newRow.append(
      `<td  class="text-center text-danger"><button onClick="DeleteCurrencyProduct(${item.id})" type="button" class="btn btn-danger" id="addButton">Delete</button></td>`
    );

    let toatal=item.currencyAmount
    totalAmount += parseFloat(toatal);
    // Append the new row to the table body
    tbody.append(newRow);
  });
  newRow1.append(`<th colspan="3" class="text-end"> Total Tax Amount:</th>`);
  newRow1.append(`<td>${totalAmount.toFixed(2)}</td>`);
  newRow1.append(`<td></td>`);
  tbody.append(newRow1);
}

$("#addCurrencyButton").click(function () {
  event.preventDefault();


  var deleteId = parseInt(document.getElementById('currencyeditId').value);
  
  if( document.getElementById('addCurrencyButton').innerText=="Update"){
    DeleteCurrencyProductAfter(deleteId)
    addcurrencyData = addcurrencyData.filter((obj) => obj.id !== deleteId);
   


  }
 document.getElementById('addCurrencyButton').innerText = "Add";

  // Get input field values
  const currencyName = $("#currencyName").val();
  const currencyNoteType = $("#currencyNoteType").val();
  const numberOfNote = $("#numberOfNote").val();
  const currencyAmount = $("#currencyAmount").val();
  const currencyId=$("#currencyId").val();
  // Create a data object to send to the API

  console.log(currencyName,currencyNoteType,numberOfNote,currencyAmount,currencyId)
  const data = {
    currencyName,
    currencyNoteType,
    numberOfNote,
    currencyAmount,
    currencyId
  };
 

  $("#table2currency").show();

  // Send a POST request to your Spring Boot API using jQuery's AJAX
  $.ajax({
    url: "/currencystart/addCurrency",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (response) {
      console.log(response);
      addcurrencyData.push(response);
      console.log(data);
      populateCurrencyTable();
      $("#currencyAmount").val("");
      // $("#quantity").val("");
      // $("#perUnitValue").val("");
      // $("#totalValue").val("");
      // $("#tax").val("");
    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });
});


function finalsubmitcurrency(){
  const currencyId=$("#id").val();
  var form = document.getElementById("myForm");
  form.action = "/currencystart/finalsubmit";
  form.submit();

}

function DeleteCurrencyProduct(id){
  console.log(id)
  const delete1 = {
    id,
  };
  addcurrencyData = addcurrencyData.filter((obj) => obj.id !== id);
  $.ajax({
    url: "/currencystart/delete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {

    populateCurrencyTable()
      // $("#quantity").val("");
      // $("#perUnitValue").val("");
      // $("#totalValue").val("");
      // $("#tax").val("");
    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });

}
function DeleteCurrencyProductAfter(id){
  
  const delete1 = {
    id,
  };
  addcurrencyData = addcurrencyData.filter((obj) => obj.id !== id);
  $.ajax({
    url: "/currencystart/delete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {
    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });

}
function EditCurrencyProduct(id){
  for (let i = 0; i < addcurrencyData.length; i++) {
    if (addcurrencyData[i].id === id) {
      document.getElementById('currencyName').value = addcurrencyData[i].currencyName;
      document.getElementById('currencyNoteType').value = addcurrencyData[i].currencyNoteType;
      document.getElementById('numberOfNote').value = addcurrencyData[i].numberOfNote;
      document.getElementById('currencyAmount').value = addcurrencyData[i].currencyAmount;
      document.getElementById('currencyeditId').value = id;
      document.getElementById('addCurrencyButton').innerText = "Update";
      // DeleteAfterEdit(idToDelete);
    
    
      
      return; // If found, exit the loop
    }
  }
  const delete1 = {
    id,
  };
  addcurrencyData = addcurrencyData.filter((obj) => obj.id !== id);
  $.ajax({
    url: "/currencystart/delete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {

    populateCurrencyTable()
      // $("#quantity").val("");
      // $("#perUnitValue").val("");
      // $("#totalValue").val("");
      // $("#tax").val("");
    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });

}

function Note_calc(){
  console.log("fahim");
  var noteType=parseFloat(document.getElementById("currencyNoteType").value);
   var numberofNote=parseFloat(document.getElementById("numberOfNote").value);

   
   var tot_amount=noteType*numberofNote;
   
      document.getElementById("currencyAmount").value=tot_amount;
 
   }


  document.addEventListener("DOMContentLoaded", function () {  
    event.preventDefault();


    //---> valueStay controller id pass <--//
    // Get the value of the 'generatedId' parameter
    const currencyId = document.getElementById("currencyId").value;
  
    $.ajax({
      url: "http://localhost:8080/currencystart/currencyformStay", // Your AJAX endpoint
      type: "POST",
      data: { currencyId: currencyId }, // Pass the baggageId as a parameter
      success: function (data) {
        // Handle the AJAX response here
        console.log(data);

    
        // if (!(data.length === 0)) {
        //   $("#table2").show();
        //   $("#table1").show();
        //   document.getElementById('dropdownSelect').value='YES';
        // }
        data.forEach(function (item) {
     
          
        addcurrencyData.push(item);
        });
       
        populateCurrencyTable();
        // ;
      },
      error: function (error) {
        // Handle AJAX errors here
        console.error(error);
      },
    });


  
   

  
  

  

  

  });