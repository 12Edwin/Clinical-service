import ref from 'vue'

const toast = ref(null)


function showToast(severity, summary, detail){
    if(toast.value){
        toast.value.add({severity, summary, detail})
    }
}

function setToastInstance(instance) {
    toast.value = instance;
}


export { showToast, setToastInstance };
