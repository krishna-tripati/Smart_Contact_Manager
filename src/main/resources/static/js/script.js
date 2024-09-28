console.log("Script loaded");
// light/dark theme
let currentTheme=getTheme();
//initial
changeTheme();

//
function changeTheme(){
//set to web page
document.querySelector("html").classList.add(currentTheme);

//set the listener to change theme button
const changeThemeButton=document.querySelector("#theme_change");

changeThemeButton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";


changeThemeButton.addEventListener("click",(event)=>{

const oldTheme=currentTheme;

console.log("change theme button clicked");

if(currentTheme ==="dark"){
currentTheme="light"
}else{
currentTheme="dark"
}

//localstorage update
setTheme(currentTheme);

//remove the current theme
document.querySelector('html').classList.remove(oldTheme);

//set the current theme
document.querySelector('html').classList.add(currentTheme);

changeThemeButton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";


});
}

//set theme to localstorage
function setTheme(theme){
localStorage.setItem("theme",theme);
}

//get them form localstorage
function getTheme(){
let theme=localStorage.getItem("theme");
return theme ? theme:"light";
}


//delete contact
   function deleteContact(cId){

    Swal.fire({
      title: "Are you sure?",
      text: "You won't to Delete this Contact!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
      const url= "http://localhost:8181/user/contacts/delete/" +cId;
      window.location.replace(url);

      }
    });
     }


     //view contact model
     const viewContactModal=document.getElementById("view_contact_modal");

     // options with default values
     const options = {
         placement: 'bottom-right',
         backdrop: 'dynamic',
         backdropClasses:
             'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
         closable: true,
         onHide: () => {
             console.log('modal is hidden');
         },
         onShow: () => {
             console.log('modal is shown');
         },
         onToggle: () => {
             console.log('modal has been toggled');
         },
     };

     // instance options object
     const instanceOptions = {
       id: 'view_contact_modal',
       override: true
     };

     const contactModal =new Modal(viewContactModal, options,instanceOptions);

     function openContactModal(){
     contactModal.show();
     }

     function closeContactModal(){
          contactModal.hide();
     }

    async function loadContactModal(cId){
    //function call to  load data
     console.log(cId);
   const data= await(
   await fetch('http://localhost:8181/api/contacts/${cId}')
   ).json();
   console.log(data);
 }


//export data
 function exportData(){
 TableToExcel.convert(document.getElementById("contact-table-export"), {
   name: "contacts.xlsx",
   sheet: {
     name: "Sheet 1"
   }
 });

 }


