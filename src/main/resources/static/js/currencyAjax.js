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
    newRow.append(`<td  >${item.currencyAmount}</td>`);
    newRow.append(
      `<td onClick="DeleteCurrencyProduct(${item.id})" class="text-center text-danger"><i class="fa-solid fa-xmark delete-button"></i></td>`
    );
    let toatal=item.currencyAmount
    totalAmount += parseFloat(toatal);
    // Append the new row to the table body
    tbody.append(newRow);
  });
  newRow1.append(`<th colspan="1" class="text-end"> Total Tax Amount:</th>`);
  newRow1.append(`<td>${totalAmount.toFixed(2)}</td>`);
  newRow1.append(`<td></td>`);
  tbody.append(newRow1);
}

$("#addButton").click(function () {
  event.preventDefault();

  // Get input field values
  const currencyName = $("#currencyName").val();
  const currencyAmount = $("#currencyAmount").val();
  const currencyId=$("#id").val();
  // Create a data object to send to the API
  const data = {
    currencyName,
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