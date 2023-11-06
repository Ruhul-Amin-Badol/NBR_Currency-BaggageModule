let addData = [];

//----->populateTable data add every click new row<-------//
function populateTable() {
  const newRow1 = $("<tr>");
  // Get a reference to the table body
  const tbody = $("#ajaxtable tbody");
  // Clear the table body before populating it again
  tbody.empty();
  let totalTax = 0.0;
  addData.forEach(function (item) {
    // Create a new table row for each item
    const newRow = $("<tr>");
    // Populate the table cells in the new row with data from the item
    newRow.append(`<td>${item.productName}</td>`); // Use the correct field names
    newRow.append(`<td>${item.unit}</td>`);
    newRow.append(`<td>${item.inchi}</td>`);
    newRow.append(`<td>${item.quantity}</td>`);
    newRow.append(`<td class="d-none" >${item.perUnitValue}</td>`);
    newRow.append(`<td>${item.totalValue}</td>`);
    newRow.append(`<td>${item.tax}</td>`);


    newRow.append(`<td>${item.cd}</td>`);
    newRow.append(`<td>${item.rd}</td>`);
    newRow.append(`<td>${item.sd}</td>`);
    newRow.append(`<td>${item.vat}</td>`);
    newRow.append(`<td>${item.ait}</td>`);
    newRow.append(`<td>${item.at}</td>`);






    newRow.append(`<td>${item.taxAmount}</td>`);
    // newRow.append(
    //   `<td  class="text-center text-danger"><i class="fa-solid fa-xmark delete-button"></i></td>`
    // );
    newRow.append(
      `<td  class="text-center text-danger"><button onClick="EditProduct(${item.id})" type="button" class="btn btn-primary" id="addButton">Edit</button></td>`
    );
    newRow.append(
      `<td  class="text-center text-danger"><button onClick="DeleteProduct(${item.id})" type="button" class="btn btn-danger" id="addButton">Delete</button></td>`
    );
    let toatal=item.taxAmount
    totalTax += parseFloat(toatal);
    // Append the new row to the table body
    tbody.append(newRow);
  });
  newRow1.append(`<th colspan="6" class="text-end"> Total Tax Amount:</th>`);
  newRow1.append(`<td>${totalTax.toFixed(2)}</td>`);
  newRow1.append(`<td></td>`);
  tbody.append(newRow1);
}

//-----> Product add button pass value for post request and show second table<----//
function adminadd() {
  

  var deleteId = parseInt(document.getElementById('deleteidhidden').value);
  
   if( document.getElementById('addButton').innerText=="Update"){
      DeleteAfterEdit(deleteId)
      addData = addData.filter((obj) => obj.id !== deleteId);
      }
  document.getElementById('addButton').innerText = "Add";

  // Get input field values
  const baggageID = $("#baggageID").val();
  const productName = $("#productName").val();
  const unit = $("#unit").val();
  const inchi = $("#inchi").val();
  const quantity = $("#quantity").val();
  const perUnitValue = $("#perUnitValue").val();
  const totalValue = $("#totalValue").val();
  const tax = $("#tax").val();
  const cd = $("#cd").val();
  const rd = $("#rd").val();
  const sd = $("#sd").val();
  const vat = $("#vat").val();
  const ait = $("#ait").val();
  const at = $("#at").val();
  const taxAmount = $("#taxAmount").val();


  // Create a data object to send to the API
  const data = {
    baggageID,
    productName,
    unit,
    inchi,
    quantity,
    perUnitValue,
    totalValue,
    tax,
    cd,
    rd,
    sd,
    vat,
    ait,
    at,

    taxAmount,
  };


  //  alert(data.taxAmount);
  if(data.taxAmount != "" & totalValue != ""){

    addData.push(data);
  }else{
    alert("Required all fields")
  }




  // Send a POST request to your Spring Boot API using jQuery's AJAX
  $.ajax({
    url: "http://localhost:8080/baggagestart/productInfo",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (response) {
      console.log(response);
      data.id = response;
      
      populateTable();
      $("#inchi").val("");
      $("#quantity").val("");
      $("#perUnitValue").val("");
      $("#totalValue").val("");
      $("#tax").val("");

      $("#cd").val("");
      $("#rd").val("");
      $("#sd").val("");
      $("#vat").val("");
      $("#ait").val("");
      $("#at").val("");

      


    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });
};


function addProduct(){

  var deleteId = parseInt(document.getElementById('deleteidhidden').value);
  
   if( document.getElementById('addButton').innerText=="Update"){
      DeleteAfterEdit(deleteId)
      addData = addData.filter((obj) => obj.id !== deleteId);
      }
  document.getElementById('addButton').innerText = "Add";

  // Get input field values
  const baggageID = $("#baggageID").val();
  const productName = $("#productName").val();
  const unit = $("#unit").val();
  const inchi = $("#inchi").val();
  const quantity = $("#quantity").val();
  const perUnitValue = $("#perUnitValue").val();
  const totalValue = $("#totalValue").val();
  const tax = $("#tax").val();
  const cd = $("#cd").val();
  const rd = $("#rd").val();
  const sd = $("#sd").val();
  const vat = $("#vat").val();
  const ait = $("#ait").val();
  const at = $("#at").val();
  const additional_payment = $("#additional_payment").val();
  const taxAmount = $("#taxAmount").val();


  // Create a data object to send to the API
  const data = {
    baggageID,
    productName,
    unit,
    inchi,
    quantity,
    perUnitValue,
    totalValue,
    tax,
    cd,
    rd,
    sd,
    vat,
    ait,
    at,
    additional_payment,

    taxAmount,
  };



  if(data.taxAmount != "" & totalValue != ""){

    addData.push(data);
  }else{
    alert("Required al fields")
  }




  // Send a POST request to your Spring Boot API using jQuery's AJAX
  $.ajax({
    url: "http://localhost:8080/baggagestart/productInfo",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (response) {
      console.log(response);
      data.id = response;
      
      populateTable();
      $("#inchi").val("");
      $("#quantity").val("");
      $("#perUnitValue").val("");
      $("#totalValue").val("");
      $("#tax").val("");

      $("#cd").val("");
      $("#rd").val("");
      $("#sd").val("");
      $("#vat").val("");
      $("#ait").val("");
      $("#at").val("");

      


    },

    error: function (error) {
      // Handle errors here
      console.error(error);
    },
  });
}


//-------> for delete product row ajax function<-----//
function DeleteProduct(idToDelete) {
  
  addData = addData.filter((obj) => obj.id !== idToDelete);
  console.log(idToDelete)
  const delete1 = {
    idToDelete,
  };
  $.ajax({
    url: "http://localhost:8080/baggagestart/productDelete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {
      
    },
    error: function (error) {
      console.error(error);
    },
  });
  populateTable();
}
function DeleteAfterEdit(idToDelete) {

  const delete1 = {
    idToDelete,
  };
  $.ajax({
    url: "http://localhost:8080/baggagestart/productDelete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {
      console.log("hi i am deleted",idToDelete);
    },
    error: function (error) {
      console.error(error);
    },
  });
  
}
function EditProduct(idToDelete) {
  for (let i = 0; i < addData.length; i++) {
    if (addData[i].id === idToDelete) {
      document.getElementById('productName').value = addData[i].productName;
      document.getElementById('unit').value = addData[i].unit;
      document.getElementById('inchi').value = addData[i].inchi;
      document.getElementById('quantity').value = addData[i].quantity;
      document.getElementById('perUnitValue').value = addData[i].perUnitValue;
      document.getElementById('totalValue').value = addData[i].totalValue;
      document.getElementById('tax').value = addData[i].tax;


      document.getElementById('cd').value = addData[i].cd;
      document.getElementById('rd').value = addData[i].rd;
      document.getElementById('sd').value = addData[i].sd;
      document.getElementById('vat').value = addData[i].vat;
      document.getElementById('ait').value = addData[i].ait;
      document.getElementById('at').value = addData[i].at;





      document.getElementById('taxAmount').value = addData[i].taxAmount;
      document.getElementById('deleteidhidden').value = idToDelete;
      document.getElementById('addButton').innerText = "Update";
      // DeleteAfterEdit(idToDelete);
    
    
      
      return; // If found, exit the loop
    }
  }

}

//--------> product Field fatch and auto show in field value<-----------//
function fetchProductData() {
  document.getElementById("quantity").value = "";
  document.getElementById("perUnitValue").value = "";
  document.getElementById("totalValue").value = "";
  document.getElementById("taxAmount").value = "";

  // Get the selected product name'
  var selectedProductName = $("#productName").val();

  if (selectedProductName !== "--Please Select--") {
   
    // Make an AJAX request to fetch product data based on the selected product name
    $.ajax({
      url: "http://localhost:8080/baggagestart/getProductData",
      method: "POST",
      data: { productString: selectedProductName },
      dataType: "json",
      success: function (data) {
        $("#unit").val(data.unit); // Update unit field
        $("#tax").val(data.taxPercentage);

        $("#cd").val(data.cd);
        $("#rd").val(data.rd);
        $("#sd").val(data.sd);
        $("#vat").val(data.vat);
        $("#ait").val(data.ait);
        $("#at").val(data.at);


        
      },
      error: function () {
        alert("Error fetching product data.");
      },

   
    });
    document.getElementById("quantity").value="0";
    document.getElementById("perUnitValue").value="0";
    document.getElementById("totalValue").value="0";
    document.getElementById("tax").value="0";
  } else {
    $("#unit").val("");
    $("#tax").val("");

    $("#cd").val("");
    $("#rd").val("");
    $("#sd").val("");
    $("#vat").val("");
    $("#ait").val("");
    $("#at").val("");



  }
}

// -------->Function to calculate and update the totalValue field<------//
function calculateTotalValue() {
  // Get the values of quantity and perUnitValue fields
  var quantity = parseFloat(document.getElementById("quantity").value);
  var perUnitValue = parseFloat(document.getElementById("perUnitValue").value);

  // Check if both values are valid numbers
  if (!isNaN(quantity) && !isNaN(perUnitValue)) {
    // Calculate the total value
    var totalValue = quantity * perUnitValue;

    // Update the totalValue field with the calculated result
    document.getElementById("totalValue").value = totalValue;
    let tax = document.getElementById("tax").value / 100;

    let cd = document.getElementById("cd").value / 100;
    let rd = document.getElementById("rd").value / 100;
    let sd = document.getElementById("sd").value / 100;
    let vat = document.getElementById("vat").value / 100;
    let ait = document.getElementById("ait").value / 100;
    let at = document.getElementById("at").value / 100;

    

    let totalTax = tax * totalValue.toFixed(2);
    let totalCd = cd * totalValue.toFixed(2);
    let totalRd= rd * totalValue.toFixed(2);
    let totalSd = sd * totalValue.toFixed(2);
    let totalvat = vat * totalValue.toFixed(2);
    let totalAit = ait * totalValue.toFixed(2);
    let totalAt = at * totalValue.toFixed(2);

    let additionTaxAmount = totalTax+totalCd+totalRd+totalSd+totalvat+totalAit+totalAt;



    document.getElementById("taxAmount").value = additionTaxAmount.toFixed(2);
  } else {
    // Handle the case where either quantity or perUnitValue is not a valid number
    document.getElementById("totalValue").value = "";
  }
  
}
// Attach the calculateTotalValue function to the 'input' event of quantity and perUnitValue fields
document
  .getElementById("quantity")
  .addEventListener("input", calculateTotalValue);
document
  .getElementById("perUnitValue")
  .addEventListener("input", calculateTotalValue);

//----->submit bottom button updete top from and bottom select option value <-------//
document.addEventListener("DOMContentLoaded", function () {
  //---> valueStay controller id pass <--//
  // Get the value of the 'generatedId' parameter
  const baggageId = document.getElementById("baggageID").value;

  $.ajax({
    url: "http://localhost:8080/baggagestart/valueStay", // Your AJAX endpoint
    type: "POST",
    data: { baggageId: baggageId }, // Pass the baggageId as a parameter
    success: function (data) {
      // Handle the AJAX response here
      console.log(data);
      console.log("sdjsdk");
  
      if (!(data.length === 0)) {
        $("#table2").show();
        $("#table1").show();
        document.getElementById('dropdownSelect').value='YES';
      }
      data.forEach(function (item) {
        // Extract data from each item and add it to the addData array
        var extractedData = {
          id: item.id,
          baggageID: item.baggage_id,
          productName: item.item_name,
          unit: item.unit_name,
          inchi: item.inchi,
          quantity: item.qty,
          perUnitValue: item.value,
          totalValue: item.tax_amount,
          tax: item.tax_percentage,

          cd: item.cd,
          rd: item.rd,
          sd: item.sd,
          tasdx: item.sd,
          vat: item.vat,
          ait: item.ait,
          at: item.at,








          taxAmount: item.tax_amount,
        };


        console.log("extractedData==========="+extractedData)
        addData.push(extractedData);



      });

      populateTable()
      ;
    },
    error: function (error) {
      // Handle AJAX errors here
      console.error(error);
    },
  });

  event.preventDefault();
  let currency = "";
  let meat = "";
  document.getElementById("idMeat").value = meat;
  document.getElementById("idCurrency").value = currency;

  console.log(meat);
  console.log(currency);

  //-----> bottom dropdown value sent<-------//
  let meatDropdown = $("#meat_product");
  let currencyDropdown = $("#foreign_currency");

  console.log(meatDropdown)
  console.log(currencyDropdown)

  meatDropdown.on("change", function () {
    sendAjaxRequest();
  });

  currencyDropdown.on("change", function () {
    sendAjaxRequest();
  });

  function sendAjaxRequest() {
    meat = meatDropdown.val();
    currency = currencyDropdown.val();

    // Check if both dropdowns have non-default values
    if (meat !== "--Please Select--" && currency !== "--Please Select--") {
      document.getElementById("idMeat").value = meat;
      document.getElementById("idCurrency").value = currency;
    }
  }
  //----> for last submit button <-----//
  var lastSubmitButtonUser = document.getElementById("lastSubmitButtonUser");
  // Add a click event listener to the last submit button
 
});
function finalSubmitUser () {

  // Manually trigger the form's submit event
  var form = document.getElementById("myForm");
  form.action = "/baggagestart/finalsubmit";
  form.submit();
};

function checkBox() {
  var accompaniedSelect = document.querySelector('select[name="accompaniedBaggageCount"]');
  var unaccompaniedSelect = document.querySelector('select[name="unaccompaniedBaggageCount"]');

  // Get the checkbox elements
  var accompaniedCheckbox = document.getElementById('flexCheckCheckedAccompanied');
  var unaccompaniedCheckbox = document.getElementById('flexCheckCheckedUnaccompanied');

  // Check if the selected values are '0' (unchecked) or not '0' (checked)
  var shouldCheckAccompanied = (accompaniedSelect.value !== '0');
  var shouldCheckUnaccompanied = (unaccompaniedSelect.value !== '0');

  // Set the checked property based on the conditions
  accompaniedCheckbox.checked = shouldCheckAccompanied;
  unaccompaniedCheckbox.checked = shouldCheckUnaccompanied;
}

window.addEventListener("load", checkBox);


//TV Inchi Calculate
function tax_calc(){
  console.log(tax_calc)
  var inchi=document.getElementById("inchi").value;
   var qty=document.getElementById("quantity").value;
   var per_unit_rate=document.getElementById("perUnitValue").value;
   
   var tot_value=qty*per_unit_rate;
   
      document.getElementById("totalValue").value=tot_value;
   var tax_perc=document.getElementById("tax").value;
   
   if(inchi>0){
   
     if(inchi >=30 && inchi <=36){
     var tax_amount=(qty*10000);
     }
     else if(inchi >=37 && inchi <=42){
     var tax_amount=(qty*20000);
     }
       else if(inchi >=43 && inchi <=46){
     var tax_amount=(qty*30000);
     }
       else if(inchi >=47 && inchi <=52){
     var tax_amount=(qty*50000);
     }
         else if(inchi >=53 && inchi <=65){
     var tax_amount=(qty*70000);
     }
           else if(inchi >=66){
     var tax_amount=(qty*90000);
     }
     else{
     var tax_amount=0;
     }
     document.getElementById("tax").value="0";
   }
   else{
   
   var tax_amount=((tot_value*tax_perc)/100).toFixed(2);
   }
   document.getElementById("taxAmount").value=tax_amount;
   }
