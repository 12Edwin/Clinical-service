<template>
    <div class="w-100 h-100">
        <h1>Valoraciones</h1>
        <div class="form fadeclass">
            <Card class="w-50 shadow-lg">
                <template #header>
                   <div class="text-start" style="border-bottom: 1px solid #d2d2d2;">
                    <h5 class="m-3">
                        ¡Compartenos tu opinión!
                    </h5>
                   </div>
                </template>
                <template #content>
                    <div class="p-fluid grid fields">
                        <b-row>
                        <div class="p-col-12">
                            <p class="rating-description">
                            ¡Califica nuestros servicios!
                            </p>
                        </div>
                        <div class="p-col-12 mb-4">
                            <Rating id="rating" :cancel="false" :stars="5" v-model="rating"/>
                        </div>
                        <div class="p-col-12 mb-4">
                            <span class="p-float-label">
                                <InputText id="name" v-model="v$.name.$model" :class="{'invalid-field-custom': v$.name.$error}" trim/>
                                <label for="name">Nombre</label>
                            </span>
                            <div class="text-danger text-start pt-2">
                                <p class="error-messages" v-if="v$.name.$dirty && v$.name.required.$invalid">
                                    {{ v$.name.required.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.name.$dirty && v$.name.onlyLettersAndAccents.$invalid">
                                    {{ v$.name.onlyLettersAndAccents.$message }}
                                </p>
                            </div>
                        </div>
                        <div class="p-col-12 mb-2">
                            <span class="p-float-label">
                                <InputTextarea id="message" rows="2" v-model="v$.comment.$model" :class="{'invalid-field-custom': v$.comment.$error}" />
                                <label for="message">Comentario</label>
                            </span>
                            <div class="text-danger text-start pt-2">
                                <p class="error-messages" v-if="v$.comment.$dirty && v$.comment.required.$invalid">
                                    {{ v$.comment.required.$message }}
                                </p>
                                <p class="error-messages" v-if="v$.comment.$dirty && v$.comment.onlyLettersAndAccents.$invalid">
                                    {{ v$.comment.onlyLettersAndAccents.$message }}
                                </p>
                            </div>
                        </div>
                        <div class="p-col-12 text-center">
                            <Button class="w-50 p-button-rounded" label="Enviar" @click="sendFeedback()" :disabled="v$.$invalid" :loading="onSave"/>
                        </div>
                        </b-row>
                    </div>
                </template>
            </Card>
        </div>
        <div class="container">
            <div  v-if="feedbacks.length !== 0">
                <h4>
                    ¿Qué dicen nuestros pacientes?
                </h4>
                <Comments :feedbacks="feedbacks"/>
            </div>
           <div v-else class="mt-2">
                <h4>
                    ¡Sé el primero en comentar!
                </h4>
           </div>
        </div>
        <Toast/>
    </div>
</template>

<script>
import Card from 'primevue/card/Card';
import InputText from 'primevue/inputtext';
import InputTextarea from 'primevue/textarea';
import Divider from 'primevue/divider/Divider';
import Rating from 'primevue/rating';
import { reactive } from '@vue/composition-api'
import { useVuelidate } from '@vuelidate/core'
import { required, helpers, maxLength, minLength} from '@vuelidate/validators'
import { regexName, text } from '@/utils/regex';
import Comments from '../components/Comments.vue';
import { decrypt, encrypt } from '@/config/security';
import { onError, onQuestion, onSuccess } from "@/kernel/alerts";
import valorationServices from "../service/valoration-services";
import Toast from 'primevue/toast/Toast';
export default {
    name: 'Feedback',
    components: {
        Card,
        InputText,
        InputTextarea,
        Divider,
        Rating,
        Comments,
        Toast
    },
    setup(){
        const feedback = reactive({
            name: '',
            comment: ''
        })

        const rules = {
            name: {
                required: helpers.withMessage('El nombre es requerido', required),
                onlyLettersAndAccents: helpers.withMessage('El nombre solo puede contener letras y acentos', (value)=>regexName.test(value))
            },
            comment: {
                required: helpers.withMessage('El comentario es requerido', required),
                onlyLettersAndAccents:helpers.withMessage('El comentario solo puede contener letras y acentos', (value)=>text.test(value))
            }
        }

        const v$ = useVuelidate(rules, feedback)
        return {
            feedback,
            v$
        }
    },
    data(){
        return {
            rating: 0,
            feedbacks: [],
            onSave: false
        }
    },
    methods: {
        async sendFeedback() {
            if(!this.v$.$invalid){
                try {
                    this.feedback.score = this.rating
                    if(await onQuestion("¿Estás seguro de enviar la valoración?", "Una vez enviada no podrá ser modificada")){
                        this.onSave = true
                        const encrypted = await encrypt(JSON.stringify(this.feedback))
                        const {status} = await valorationServices.saveValoration(encrypted)
                        if(status === 200){
                            this.onSave = false
                            await onSuccess("Valoración enviada", "Gracias por compartir tu opinión")
                            this.getFeedbacks()
                            this.cleanFields()
                        }
                    }
                } catch (error) {
                    this.onSave = false
                    await onError("Error al enviar la valoración", "Parece que estamos teniendo problemas con el servidor")
                }
            }else{
                this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail: '¡Parece que las horas ingresadas no son válidas!', life: 3000});
            }
        },
        async getFeedbacks(){
            try {
                const {status, data: {result}} = await valorationServices.getValorations()
                if(status === 200){
                    this.feedbacks = JSON.parse(await decrypt(result)).content
                }
            } catch (error) {
                await onError("Error al obtener los comentarios", "Parece que estamos teniendo problemas con el servidor")
            }
        },
        cleanFields(){
            this.v$.name.$model = ''
            this.v$.comment.$model = ''
            this.rating = 0
            this.v$.$reset()
        }
    },
    mounted(){
        this.getFeedbacks()
    }
}
</script>

<style scoped>

.subtitle{
    color: #4c4c4c;
    font-size: 1.5em;
    font-weight: 600;
    margin-bottom: 2em;
}
.form{
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 1em;
    margin-bottom: 1em;
}
.label-rating{
    font-size: 1.0em;
    margin-left: 4px;
    color: #495057 !important;
}


.fields label::after {
    content: " *";
    color: red;

}

.h1-feed{
  font-family: 'Arial', sans-serif;
  font-size: 18px;
  font-weight: bold;
  color: #333; 
  margin-bottom: 8px; 
  text-align: left; 
}

.rating-description{
  font-family: 'Arial', sans-serif;
  font-size: 18px;
  font-weight: normal; 
  color: #666;
  margin-top: 0;
  text-align: center;
  line-height: 1.5;
}

.invalid-field-custom{
    border-color:  #ff0000 !important;
    box-shadow: 0 0 3px rgba(255, 0, 0, 0.4) !important;
}

.error-messages{
    margin-bottom: 0; font-weight: 350; font-size: 15px;
}

.error-messages::before{
    content: "* ";
    color: #ff0000;
}

.fadeclass{
  animation-name: fade;
  animation-duration: 1s;
}

@keyframes fade {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>

<style >

.p-rating .p-rating-icon {
    font-size: 2em !important;
    color: #ffc929 !important;
}
</style>