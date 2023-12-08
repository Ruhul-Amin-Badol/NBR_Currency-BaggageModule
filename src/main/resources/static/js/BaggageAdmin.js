let addDataAdmin = [];

//----->populateTableAdmin data add every click new row<-------//
function populateTableAdmin() {


  
  const newRow1 = $("<tr>");
  // Get a reference to the table body
  const tbody = $("#ajaxtable tbody");
  // Clear the table body before populating it again
  tbody.empty();
  let totalTax = 0.0;
  let  totalAdditionalPayment=0.0
  addDataAdmin.forEach(function (item) {
    // Create a new table row for each item

    console.log("hi i am from add")
    console.log(item)
    let newRow = $("<tr>");
    if(item.duty_free !=0 && item.quantity>item.duty_free) {
      
      newRow.append(`<td style="color: red;">${item.productName}</td>`); // Use the correct field names
      newRow.append(`<td style="color: red;">${item.otherItem}</td>`); // Use the correct field names
      newRow.append(`<td style="color: red;">${item.unit}</td>`);
      newRow.append(`<td style="color: red;">${item.inchi}</td>`);
      newRow.append(`<td style="color: red;">${item.quantity}</td>`);
      newRow.append(`<td style="color: red;" class="d-none" >${item.perUnitValue}</td>`);
      newRow.append(`<td style="color: red;">${item.totalValue}</td>`);
      newRow.append(`<td style="color: red;">${item.tax}</td>`);
      newRow.append(`<td style="color: red;">${item.cd}</td>`);
      newRow.append(`<td style="color: red;">${item.rd}</td>`);
      newRow.append(`<td style="color: red;">${item.sd}</td>`);
      newRow.append(`<td style="color: red;">${item.vat}</td>`);
      newRow.append(`<td style="color: red;">${item.ait}</td>`);
      newRow.append(`<td style="color: red;">${item.at}</td>`);
      // newRow.append(`<td style="color: red;">${item.at}</td>`);
      newRow.append(`<td style="color: red;">${item.taxAmount}</td>`);


    } else {

     newRow = $("<tr>");
     newRow.append(`<td>${item.productName}</td>`); // Use the correct field names
     newRow.append(`<td>${item.otherItem}</td>`); // Use the correct field names
     newRow.append(`<td>${item.unit}</td>`);
     newRow.append(`<td>${item.inchi}</td>`);
     newRow.append(`<td >${item.quantity}</td>`);
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
    }
  
    if(item.additional_payment==null){
      item.additional_payment=0;
      
    }
    newRow.append(`<td>${item.additional_payment}</td>`);
   if(item.paymentStatus=="Paid"){
    
    newRow.append(`<td></td>`);
    newRow.append(`<td></td>`);
   }else{
    newRow.append(
      `<td  class="text-center text-danger"><button onClick="EditProductAdmin(${item.id})" type="button" class="btn btn-primary" id="addButtonAdmin"><i class="fa-solid fa-pen-to-square"></i></button></td>`
    );
    newRow.append(
      `<td  class="text-center text-danger"><button onClick="DeleteProductAdmin(${item.id})" type="button" class="btn btn-danger" id="addButtonAdmin"><i class="fa-solid fa-trash"></i></button></td>`
    );
   }

    let toatal=item.taxAmount
    totalTax += parseFloat(toatal);
    

    let additionalPayment = item.additional_payment;
    if(additionalPayment == ""){
      additionalPayment = 0;
    }
    totalAdditionalPayment +=  parseFloat(additionalPayment);
  

    // Append the new row to the table body
    tbody.append(newRow);
  });
  var previousPaidAmount=document.getElementById("totalPaidAmountId").value
  var parsedPreviousPaidAmount = parseFloat(previousPaidAmount);

  console.log("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhjjjjjjjjjjjjj"+parsedPreviousPaidAmount);

  let totalPayableAmount = totalTax + totalAdditionalPayment;
  totalPayableAmount = totalPayableAmount-parsedPreviousPaidAmount

  newRow1.append(`<th colspan="3" class="text-end"> Total Tax Amount:</th>`);
  newRow1.append(`<td>${totalTax.toFixed(2)}</td>`);


  newRow1.append(`<th colspan="4" class="text-end"> Additional Payment:</th>`);
  newRow1.append(`<td>${totalAdditionalPayment.toFixed(2)}</td>`);

  newRow1.append(`<th colspan="4" class="text-end"> Payable Amount:</th>`);
  newRow1.append(`<td colspan="4">${totalPayableAmount.toFixed(2)}</td>`);


  // newRow1.append(`<th colspan="6" class="text-end"> Pdditional Payment:</th>`);
  // newRow1.append(`<td>${parseFloat.totalAdditionalPayment(2)}</td>`);
  tbody.append(newRow1);
}

//-----> Product add button pass value for post request and show second table<----//
function adminadd() {
 
 
  var deleteId = parseInt(document.getElementById('deleteidhidden').value);

   if( document.getElementById('addButtonAdmin').innerText=="Update"){
      DeleteAfterEdit(deleteId)
      addDataAdmin = addDataAdmin.filter((obj) => obj.id !== deleteId);
      
      }
  document.getElementById('addButtonAdmin').innerText = "Add";

  // Get input field values
  const baggageID = $("#baggageID").val();
  const productName = $("#productName").val();
  const otherItem = $("#otherItem").val();
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
    otherItem,
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

    // $.ajax({

    //   url: "/baggagestart/productInfo",
    //   type: "POST",
    //   contentType: "application/json",
    //   data: JSON.stringify(data),
    //   success: function (response) {
    //     console.log(response);
    //     data.id = response;
        
    //     populateTableAdmin();
    //     $("#inchi").val("");
    //     $("#quantity").val("");
    //     $("#perUnitValue").val("");
    //     $("#totalValue").val("");
    //     $("#tax").val("");
  
    //     $("#cd").val("");
    //     $("#rd").val("");
    //     $("#sd").val("");
    //     $("#vat").val("");
    //     $("#ait").val("");
    //     $("#at").val("");
    //     $("#additional_payment").val("");
        
    //   },
  
  
    //   error: function (error) {
    //     // Handle errors here
    //     console.error(error);
    //   },
      
    // });
  
    $.ajax({
      url: "/baggagestart/productInfo",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function (item) {
        console.log(item);
        // data.id = response;
      
      console.log(item);
        var extractedData = {
          id: item.id,
          item_id:item.item_id,
          duty_free:item.duty_free,
          baggageID: item.baggage_id,
          productName: item.item_name,
          otherItem: item.other_item,
          unit: item.unit_name,
          inchi: item.inchi,
          quantity: item.qty,
          perUnitValue: item.value,
          totalValue: item.qty * item.value,
          tax: item.tax_percentage,
  
          cd: item.cd,
          rd: item.rd,
          sd: item.sd,
          tasdx: item.sd,
          vat: item.vat,
          ait: item.ait,
          at: item.at,
          additional_payment: item.additional_payment,
         taxAmount: item.tax_amount,
         paymentStatus:item.payment_status,
        };
        
        addDataAdmin.push(extractedData);
        
        populateTableAdmin();
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
        
        // populateTable();
       
        // $("#inchi").val("");
        // $("#quantity").val("");
        // $("#perUnitValue").val("");
        // $("#totalValue").val("");
        // $("#tax").val("");
  
        // $("#cd").val("");
        // $("#rd").val("");
        // $("#sd").val("");
        // $("#vat").val("");
        // $("#ait").val("");
        $("#at").val("");
  
        
  
  
      },
  
      error: function (error) {
        // Handle errors here
        console.error(error);
      },
    });


  }else{
    alert("Required all fields")
  }
  // Send a POST request to your Spring Boot API using jQuery's AJAX

};

//-------> for delete product row ajax function<-----//

function DeleteAfterEdit(idToDelete) {

  const delete1 = {
    idToDelete,
  };
  $.ajax({
    url: "/baggagestart/productDelete",
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

function DeleteProductAdmin(idToDelete) {

    addDataAdmin = addDataAdmin.filter((obj) => obj.id !== idToDelete);
  const delete1 = {
    idToDelete,
  };
  $.ajax({
    url: "/baggagestart/productDelete",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(delete1),
    success: function (response) {
     
      populateTableAdmin();
    },
    error: function (error) {
      console.error(error);
    },
  });

}
function EditProductAdmin(idToDelete) {
  for (let i = 0; i < addDataAdmin.length; i++) {
    if (addDataAdmin[i].id === idToDelete) {
      if(addDataAdmin[i].productName=='Other'){



    
        document.getElementById('otherItem').style.display = 'block';
        document.getElementById('otherItem').value=addDataAdmin[i].otherItem;

        // Set the selectedIndex to make the option selected
   

      }else{
        document.getElementById('otherItem').style.display = 'none';
        document.getElementById('otherItem').value='';
      }
//new adds


      document.getElementById('productName').value = addDataAdmin[i].productName;
      document.getElementById('unit').value = addDataAdmin[i].unit;
      document.getElementById('inchi').value = addDataAdmin[i].inchi;
      document.getElementById('quantity').value = addDataAdmin[i].quantity;
      document.getElementById('perUnitValue').value = addDataAdmin[i].perUnitValue;
      document.getElementById('totalValue').value = addDataAdmin[i].totalValue;
      document.getElementById('tax').value = addDataAdmin[i].tax;


      document.getElementById('cd').value = addDataAdmin[i].cd;
      document.getElementById('rd').value = addDataAdmin[i].rd;
      document.getElementById('sd').value = addDataAdmin[i].sd;
      document.getElementById('vat').value = addDataAdmin[i].vat;
      document.getElementById('ait').value = addDataAdmin[i].ait;
      document.getElementById('at').value = addDataAdmin[i].at;


      document.getElementById('additional_payment').value = addDataAdmin[i].additional_payment;
      
      document.getElementById('taxAmount').value = addDataAdmin[i].taxAmount;
      document.getElementById('deleteidhidden').value = idToDelete;
      document.getElementById('addButtonAdmin').innerText = "Update";
      // DeleteProductAdmin(idToDelete)
      
      return; 
      // If found, exit the loop
    }
  }

}

//--------> product Field fatch and auto show in field value<-----------//
function fetchProductDataAdmin() {
   
  document.getElementById("quantity").value = "";
  document.getElementById("perUnitValue").value = "";
  document.getElementById("totalValue").value = "";
  document.getElementById("taxAmount").value = "";

  // Get the selected product name'
  var selectedProductName = $("#productName").val();
  if (selectedProductName == "Other"){
    document.getElementById('otherItem').style.display = 'block';
    // $("#inchi").val("");
    // $("#quantity").val("");
    $("#perUnitValue").val(0);
    // $("#totalValue").val("");
    $("#tax").val(0);
    $("#cd").val(0);
    $("#rd").val(0);``
    $("#sd").val(0);
    $("#vat").val(0);
    $("#ait").val(0);
    $("#at").val(0);
  }

  else if (selectedProductName !== "--Please Select--") {
    document.getElementById('otherItem').style.display = 'none';
   
   
    // Make an AJAX request to fetch product data based on the selected product name
    $.ajax({
      url: "/baggagestart/getProductData",
      method: "POST",
      data: { productString: selectedProductName },
      dataType: "json",
      success: function (data) {
        document.getElementById("perUnitValue").value=data.perunitvalue;
        $("#unit").val(data.unit); // Update unit field
        $("#tax").val(data.taxPercentage);

        $("#cd").val(data.cd);
        $("#rd").val(data.rd);
        $("#sd").val(data.sd);
        $("#vat").val(data.vat);
        $("#ait").val(data.ait);
        $("#at").val(data.at);

        $("#additional_payment").val(data.additional_payment);
        
      },
      error: function () {
        alert("Error fetching product data.");
      },

   
    });
  } else {
    $("#unit").val("");
    $("#tax").val("");

    $("#cd").val("");
    $("#rd").val("");
    $("#sd").val("");
    $("#vat").val("");
    $("#ait").val("");
    $("#at").val("");

    $("#additional_payment").val("");
    



  }
}

// function fetchProductDataAdmin() {
   
//   document.getElementById("quantity").value = "";
//   document.getElementById("perUnitValue").value = "";
//   document.getElementById("totalValue").value = "";
//   document.getElementById("taxAmount").value = "";

//   // Get the selected product name'
//   var selectedProductName = $("#productName").val();
//   if (selectedProductName == "Other"){
//     document.getElementById('otherItem').style.display = 'block';
//     // $("#inchi").val("");
//     // $("#quantity").val("");
//     // $("#perUnitValue").val("");
//     // $("#totalValue").val("");
//     $("#tax").val(0);
//     $("#cd").val(0);
//     $("#rd").val(0);
//     $("#sd").val(0);
//     $("#vat").val(0);
//     $("#ait").val(0);
//     $("#at").val(0);
//   }

//   else if (selectedProductName !== "--Please Select--") {
//     document.getElementById('otherItem').style.display = 'none';
   
//     // Make an AJAX request to fetch product data based on the selected product name
//     $.ajax({
//       url: "http://localhost:8080/baggagestart/getProductData",
//       method: "POST",
//       data: { productString: selectedProductName },
//       dataType: "json",
//       success: function (data) {
//         $("#unit").val(data.unit); // Update unit field
//         $("#tax").val(data.taxPercentage);
//         $("#cd").val(data.cd);
//         $("#rd").val(data.rd);
//         $("#sd").val(data.sd);
//         $("#vat").val(data.vat);
//         $("#ait").val(data.ait);
//         $("#at").val(data.at);

//         $("#additional_payment").val(data.additional_payment);
        
//       },
//       error: function () {
//         alert("Error fetching product data.");
//       },

   
//     });
//     document.getElementById("quantity").value="0";
//     document.getElementById("perUnitValue").value="0";
//     document.getElementById("totalValue").value="0";
//     document.getElementById("tax").value="0";
//   } else {
//     $("#unit").val("");
//     $("#tax").val("");

//     $("#cd").val("");
//     $("#rd").val("");
//     $("#sd").val("");
//     $("#vat").val("");
//     $("#ait").val("");
//     $("#at").val("");

//     $("#additional_payment").val("");
    



  // }
// -------->Function to calculate and update the totalValue field<------//
function calculateTotalValueAdmin() {
  // Get the values of quantity and perUnitValue fields
  const productnameforgold=document.getElementById('productName').value
  var quantity = parseFloat(document.getElementById("quantity").value);
  var perUnitValue = parseFloat(document.getElementById("perUnitValue").value);

  // Check if both values are valid numbers
  if ( (productnameforgold=="স্বর্ণবার বা স্বর্ণপিন্ড(সর্বোচ্চ ২০০ গ্রাম)" || productnameforgold=="রৌপ্যবার বা রৌপ্যপিন্ড (সর্বোচ্চ ২০০ গ্রাম)") && !isNaN(quantity) && quantity>200) {

    // Calculate the total value
   
    const payblequantity=quantity-200;
  
    
    var totalValue = payblequantity * 12.8600823;
    
    console.log(totalValue);
    // Update the totalValue field with the calated result
    document.getElementById("totalValue").value = totalValue;
  
    let tax = document.getElementById("tax").value / 100;
  
    let cd = document.getElementById("cd").value / 100;
    let rd = document.getElementById("rd").value / 100;
    let sd = document.getElementById("sd").value / 100;
    let vat = document.getElementById("vat").value / 100;
    let ait = document.getElementById("ait").value / 100;
    let at = document.getElementById("at").value / 100;

  
    
  
    let totalTax =  totalValue.toFixed(2);

    let totalCd = cd * totalValue.toFixed(2);
    let totalRd= rd * totalValue.toFixed(2);
    let totalSd = sd * totalValue.toFixed(2);
    let totalvat = vat * totalValue.toFixed(2);
    let totalAit = ait * totalValue.toFixed(2);
    let totalAt = at * totalValue.toFixed(2);
  
    let additionTaxAmount = totalTax+totalCd+totalRd+totalSd+totalvat+totalAit+totalAt;
  
  
  
    document.getElementById("taxAmount").value = additionTaxAmount;
  }
  
  
    else if (!isNaN(quantity) && !isNaN(perUnitValue)) {
      
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
    } 
    else {
      // Handle the case where either quantity or perUnitValue is not a valid number
      document.getElementById("totalValue").value = "";
    }
  
}
// Attach the calculateTotalValueAdmin function to the 'input' event of quantity and perUnitValue fields
document
  .getElementById("quantity")
  .addEventListener("input", calculateTotalValueAdmin);
document
  .getElementById("perUnitValue")
  .addEventListener("input", calculateTotalValueAdmin);

//----->submit bottom button updete top from and bottom select option value <-------//
document.addEventListener("DOMContentLoaded", function () {
  //---> valueStay controller id pass <--//
  // Get the value of the 'generatedId' parameter
  const baggageId = document.getElementById("baggageID").value;

  $.ajax({
    url: "/baggagestart/valueStay", // Your AJAX endpoint
    type: "POST",
    data: { baggageId: baggageId }, // Pass the baggageId as a parameter
    success: function (data) {
      // Handle the AJAX response here
   
  
      if (!(data.length === 0)) {
        $("#table2").show();
        $("#table1").show();
        document.getElementById('dropdownSelect').value='YES';
      }
      data.forEach(function (item) {
        // Extract data from each item and add it to the addDataAdmin array
        var extractedData = {
          id: item.id,
          item_id:item.item_id,
          duty_free:item.duty_free,
          baggageID: item.baggage_id,
          productName: item.item_name,
          otherItem: item.other_item,
          unit: item.unit_name,
          inchi: item.inchi,
          quantity: item.qty,
          perUnitValue: item.value,
          totalValue: item.qty * item.value,
          tax: item.tax_percentage,

          cd: item.cd,
          rd: item.rd,
          sd: item.sd,
          tasdx: item.sd,
          vat: item.vat,
          ait: item.ait,
          at: item.at,
          additional_payment: item.additional_payment,
          taxAmount: item.tax_amount,
          paymentStatus:item.payment_status,
        };


        console.log("extractedData==========="+extractedData)
        addDataAdmin.push(extractedData);



      });

      populateTableAdmin()
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
  var lastSubmitButton = document.getElementById("lastSubmitButton");
  // Add a click event listener to the last submit button
  lastSubmitButton.addEventListener("click", function () {
    // Manually trigger the form's submit event
    var form = document.getElementById("myForm");
    form.action = "/baggagestart/finalsubmitAdmin";
    form.submit();
  });
});

function checkBoxAdmin() {
  var accompaniedSelect = document.querySelector('select[name="accompaniedBaggageCount"]');
  var unaccompaniedSelect = document.querySelector('select[name="unaccompaniedBaggageCount"]');

  // Get the checkBoxAdmin elements
  var accompaniedCheckbox = document.getElementById('flexCheckCheckedAccompanied');
  var unaccompaniedCheckbox = document.getElementById('flexCheckCheckedUnaccompanied');

  // Check if the selected values are '0' (unchecked) or not '0' (checked)
  var shouldCheckAccompanied = (accompaniedSelect.value !== '0');
  var shouldCheckUnaccompanied = (unaccompaniedSelect.value !== '0');

  // Set the checked property based on the conditions
  accompaniedCheckbox.checked = shouldCheckAccompanied;
  unaccompaniedCheckbox.checked = shouldCheckUnaccompanied;
}

window.addEventListener("load", checkBoxAdmin);



//TV Inchi Calculate
function tax_calcAdmin(){
  const producttv=document.getElementById('productName').value
  if(producttv=="TV"){
  console.log(tax_calcAdmin)
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
   }
