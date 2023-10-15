const mobileScreen = window.matchMedia("(max-width: 990px )");
$(document).ready(function () {
    $(".dashboard-nav-dropdown-toggle").click(function () {
        $(this).closest(".dashboard-nav-dropdown")
            .toggleClass("show")
            .find(".dashboard-nav-dropdown")
            .removeClass("show");
        $(this).parent()
            .siblings()
            .removeClass("show");
    });
    $(".menu-toggle").click(function () {
        if (mobileScreen.matches) {
            $(".dashboard-nav").toggleClass("mobile-show");
        } else {
            $(".dashboard").toggleClass("dashboard-compact");
        }
    });
});

// Function to toggle the visibility of the additional checkbox input box
function toggleDiv(divId) {
    var div = document.getElementById(divId);
    if (div.style.display === "none") {
        div.style.display = "block";
    } else {
        div.style.display = "none";
    }
}



function toggleDropdown() {
    const dropdown = document.querySelector('.select-wrapper');
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
}

// Function to filter options based on the search input
function toggleDropdown() {
    const countrySelect = document.querySelector('.country-select');
    countrySelect.style.display = countrySelect.style.display === 'none' ? 'block' : 'none';
}

// Function to filter options based on the search input
function toggleDropdown() {
    const searchInput = document.querySelector('.search-input');
    const selectInput = document.querySelector('.select-input');
    if (searchInput.style.display === 'none') {
        searchInput.style.display = 'block';
        selectInput.style.display = 'none';
        document.getElementById('countrySearch').focus();
    } else {
        searchInput.style.display = 'none';
        selectInput.style.display = 'block';
    }
}

// Function to filter options based on the search input
$(document).ready(function() {
    // Initialize Select2 on the "previous_country" select element
    $("#previous_country").select2();
});

$(document).ready(function() {
    // Initialize Select2 on the "previous_country" select element
    $("#productName").select2();
});

 // Function to toggle the visibility of the additional input box
 function toggleOtherInput() {
    const nationalitySelect = document.getElementById('nationality');
    const otherInput = document.getElementById('otherNationality');

    if (nationalitySelect.value === 'Other') {
      otherInput.style.display = 'block';
    } else {
      otherInput.style.display = 'none';
    }
  }

  // Add an event listener to the select element to trigger the toggle function
  document.addEventListener('DOMContentLoaded', function() {
    const nationalitySelect = document.getElementById('nationality');
    nationalitySelect.addEventListener('change', toggleOtherInput);
    toggleOtherInput(); // Initially check the state
  });

   // JavaScript/jQuery to show/hide tables based on dropdown selection
//    $(document).ready(function () {
//     // Listen for changes in the dropdown select element
//     $('#dropdownSelect').change(function () {
//         // Get the selected value
//         var selectedValue = $(this).val();
        
//         // Show or hide tables based on the selected value
//         if (selectedValue === 'YES') {
//             $('#table1').show();
//             $('#table2').show();
//         } else {
//             $('#table1').hide();
//             $('#table2').hide();
//         }
//     });
// });

// JavaScript code to open a new window and display the PDF or image
document.getElementById('openFileButton').addEventListener('click', function () {
    // Specify the URL of the PDF or image file
    var fileUrl = 'http://bcom.gov.bd/nbr/baggage_mod/pages/baggage2/view_pdf.php'; // Replace with your file URL

    // Open a new window or tab with the file
    window.open(fileUrl, '_blank');
});

//----->langguage model<-----//
function toggleLanguage(language) {
    var labels = {
        'bangla': {
            'entryPointLabel': 'এন্ট্রি পয়েন্ট নির্বাচন করুন / আগমনের স্থান',
            'passengerNameLabel': 'যাত্রীর নাম (পাসপোর্টের সাথে সমান)',
            'passportNumberLabel': 'পাসপোর্ট নম্বর',
            'passportValidityDateLabel': 'পাসপোর্টের মেয়াদ সমাপ্তির তারিখ',
            'nationalityLabel': 'জাতীয়তা',
            'bangladeshOption': 'বাংলাদেশ',
            'otherOption': 'অন্যান্য জাতীয়তা',
            'otherNationalityLabel': 'অন্যান্য জাতীয়তা',
            'countryFromLabel': 'কোন দেশ হইতে আগমন',
            'dateOfArrivalLabel': 'আগমনের তারিখ',
            'flightNoLabel': 'ফ্লাইট নম্বর',
            'mobileNoLabel': 'মোবাইল নম্বর',
            'emailLabel': 'ইমেইল',
            'accompaniedLabel': 'নিজ ব্যাগেজ ',
            'unaccompaniedLabel':'অন্যের ব্যাগেজ' ,
            
            'fullnameLabel':'পুরো নাম' ,
            'previouscountryLabel':'যে দেশ থেকে আসছে' ,
            'bangladeshaddreessLabel':'বাংলাদেশে ঠিকানা' ,
            'profssionLabel':'পেশা' ,
        },
        'english': {
            'entryPointLabel': 'Select Entry Point',
            'passengerNameLabel': 'Name of the Passenger(Same As Passport)',
            'passportNumberLabel': 'Passport Number',
            'passportValidityDateLabel': 'Passport Validity Date',
            'nationalityLabel': 'Nationality',
            'bangladeshOption': 'Bangladesh',
            'otherOption': 'Other',
            'otherNationalityLabel': 'Other Nationality',
            'countryFromLabel': 'Country from where coming',
            'dateOfArrivalLabel': 'Date Of Arrival',
            'flightNoLabel': 'Flight No',
            'mobileNoLabel': 'Mobile No',
            'emailLabel': 'Email',
            'accompaniedLabel': 'Accompanied',
            'unaccompaniedLabel': 'Un-Accompanied',

            'fullnameLabel':'Full Name' ,
            'previouscountryLabel':'Country from where coming' ,
            'bangladeshaddreessLabel':'Address in Bangladesh' ,
            'profssionLabel':'Profession' ,
        }
    };

    for (var label in labels[language]) {
        var element = document.getElementById(label);
        if (element) {
            element.textContent = labels[language][label];
        }
    }
}



//baggage js start

// Function to toggle the visibility of the additional checkbox input box
function toggleDiv(divId) {
    var div = document.getElementById(divId);
    if (div.style.display === "none") {
        div.style.display = "block";
    } else {
        div.style.display = "none";
    }
}



function toggleDropdown() {
    const dropdown = document.querySelector('.select-wrapper');
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
}

// Function to filter options based on the search input
function toggleDropdown() {
    const countrySelect = document.querySelector('.country-select');
    countrySelect.style.display = countrySelect.style.display === 'none' ? 'block' : 'none';
}

// Function to filter options based on the search input
function toggleDropdown() {
    const searchInput = document.querySelector('.search-input');
    const selectInput = document.querySelector('.select-input');
    if (searchInput.style.display === 'none') {
        searchInput.style.display = 'block';
        selectInput.style.display = 'none';
        document.getElementById('countrySearch').focus();
    } else {
        searchInput.style.display = 'none';
        selectInput.style.display = 'block';
    }
}

// Function to filter options based on the search input
$(document).ready(function() {
    // Initialize Select2 on the "previous_country" select element
    $("#previous_country").select2();
});

$(document).ready(function() {
    // Initialize Select2 on the "previous_country" select element
    $("#productName").select2();
});

 // Function to toggle the visibility of the additional input box
 function toggleOtherInput() {
    const nationalitySelect = document.getElementById('nationality');
    const otherInput = document.getElementById('otherNationality');

    if (nationalitySelect.value === 'Other') {
      otherInput.style.display = 'block';
    } else {
      otherInput.style.display = 'none';
    }
  }

  // Add an event listener to the select element to trigger the toggle function
  document.addEventListener('DOMContentLoaded', function() {
    const nationalitySelect = document.getElementById('nationality');
    nationalitySelect.addEventListener('change', toggleOtherInput);
    toggleOtherInput(); // Initially check the state
  });

   // JavaScript/jQuery to show/hide tables based on dropdown selection
   $(document).ready(function () {
    // Listen for changes in the dropdown select element
    $('#dropdownSelect').change(function () {
        // Get the selected value
        var selectedValue = $(this).val();
        
        // Show or hide tables based on the selected value
        if (selectedValue === 'YES') {
            $('#table1').show();
            $('#table2').show();
        } else {
            $('#table1').hide();
            $('#table2').hide();
        }
    });
});

// JavaScript code to open a new window and display the PDF or image
document.getElementById('openFileButton').addEventListener('click', function () {
    // Specify the URL of the PDF or image file
    var fileUrl = 'http://bcom.gov.bd/nbr/baggage_mod/pages/baggage2/view_pdf.php'; // Replace with your file URL

    // Open a new window or tab with the file
    window.open(fileUrl, '_blank');
});

//----->langguage model<-----//
function toggleLanguage(language) {
    var labels = {
        'bangla': {
            'entryPointLabel': 'এন্ট্রি পয়েন্ট নির্বাচন করুন / আগমনের স্থান',
            'passengerNameLabel': 'যাত্রীর নাম (পাসপোর্টের সাথে সমান)',
            'passportNumberLabel': 'পাসপোর্ট নম্বর',
            'passportValidityDateLabel': 'পাসপোর্টের মেয়াদ সমাপ্তির তারিখ',
            'nationalityLabel': 'জাতীয়তা',
            'bangladeshOption': 'বাংলাদেশ',
            'otherOption': 'অন্যান্য জাতীয়তা',
            'otherNationalityLabel': 'অন্যান্য জাতীয়তা',
            'countryFromLabel': 'কোন দেশ হইতে আগমন',
            'dateOfArrivalLabel': 'আগমনের তারিখ',
            'flightNoLabel': 'ফ্লাইট নম্বর',
            'mobileNoLabel': 'মোবাইল নম্বর',
            'emailLabel': 'ইমেইল',
            'accompaniedLabel': 'নিজ ব্যাগেজ ',
            'unaccompaniedLabel':'অন্যের ব্যাগেজ' 
        },
        'english': {
            'entryPointLabel': 'Select Entry Point',
            'passengerNameLabel': 'Name of the Passenger(Same As Passport)',
            'passportNumberLabel': 'Passport Number',
            'passportValidityDateLabel': 'Passport Validity Date',
            'nationalityLabel': 'Nationality',
            'bangladeshOption': 'Bangladesh',
            'otherOption': 'Other',
            'otherNationalityLabel': 'Other Nationality',
            'countryFromLabel': 'Country from where coming',
            'dateOfArrivalLabel': 'Date Of Arrival',
            'flightNoLabel': 'Flight No',
            'mobileNoLabel': 'Mobile No',
            'emailLabel': 'Email',
            'accompaniedLabel': 'Accompanied',
            'unaccompaniedLabel': 'Un-Accompanied'
        }
    };

    for (var label in labels[language]) {
        var element = document.getElementById(label);
        if (element) {
            element.textContent = labels[language][label];
        }
    }
}

//baggage js end