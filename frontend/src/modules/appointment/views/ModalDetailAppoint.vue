<template>
    <b-row>
        <b-col cols="12">
            <Dialog id="dialog" :visible.sync="visible" :containerStyle="{ width: '40vw'}" :contentStyle="{overflow: 'visible'}" @hide="() => closeModal()" :modal="true" :closeOnEscape="false" :closable="false">
                <template #header>
                    <div class="d-flex justify-content-between w-100">
                        <h4>Detalle de la cita</h4>
                        <Chip :style="{'background-color': setColorByStatus(appointInfo.status), 'color' : 'white'}" :label="appointInfo.status" :icon="setIconByStatus(appointInfo.status)" />
                    </div>
	            </template>
                <div class="p-fluid grid">
                    <b-row class="mt-1 mb-2">
                        <b-col>
                            <div class="field text-start" v-if="appointInfo.status !== 'Completada' && appointInfo.status !== 'Cancelada'">
                                <span class="p-float-label">
                                    <Dropdown 
                                        id="options-selector" 
                                        class="text-start" 
                                        v-model="optionSelected" 
                                        :options="options"
                                        optionLabel="name" 
                                        optionValue="name" 
                                    />
                                    <label for="options-selector" class="form-label-required">Acción:</label>
                                </span>
                            </div>
                        </b-col>
                    </b-row>
                    <div>
                        <b-row class="mt-3">
                        <b-col>
                            <div class="field mt-3">
                                <span class="p-float-label p-input-icon-right">
                                    <i class="pi pi-calendar" />
                                    <Calendar id="disableddays" :minDate="new Date()" v-model="appointInfo.date" :dateFormat="'dd/mm/yy'" :disabledDays="[0,6]" :manualInput="false" :readonly="!isEditable()"/>
                                    <label for="field-date" class="form-label-required">Fecha:</label>
                                </span>
                            </div>
                        </b-col>
                        </b-row>
                        <b-row class="mt-3">
                            <b-col lg="6">
                                <div class="field text-start mt-3">
                                    <span class="p-float-label p-input-icon-right">
                                        <Calendar id="start-hour-field" :timeOnly="true" hourFormat="12" showTime
                                            :manualInput="false" v-model="appointInfo.startHour"
                                            :inputStyle="{ 'border-color': areSame || isBeforeEnd || isBeforeCurrent ? '#ff0000' : ''}" @input="validateHours()"
                                            :readonly="!isEditable()"  />
                                        <label for="start-hour-field" class="form-label-required">
                                            Hora inicio</label>
                                    </span>
                                    <div class="text-danger text-danger text-start pt-2">
                                        <p class="error-messages" v-if="areSame">
                                            Las hora de incio no pueden ser iguales
                                        </p>
                                        <p class="error-messages" v-if="isBeforeEnd">
                                            La hora de inicio no puede ser mayor a la hora de
                                            fin
                                        </p>
                                        <p class="error-messages" v-if="isBeforeCurrent">
                                            La hora de inicio no puede ser menor a la hora
                                            actual
                                        </p>
                                    </div>
                                </div>
                            </b-col>
                            <b-col>
                                <div class="field text-start mt-3">
                                    <span class="p-float-label p-input-icon-right">
                                        <Calendar id="start-hour-field" :timeOnly="true" hourFormat="12" showTime :readonly="!isEditable()"
                                            :manualInput="false" v-model="appointInfo.endHour"
                                            :inputStyle="{ 'border-color': areSame || isGraterThanFive ? '#ff0000' : ''}" @input="validateHours()"/>
                                        <label for="start-hour-field" class="form-label-required">
                                            Hora fin</label>
                                    </span>
                                    <div class="text-danger text-danger text-start pt-2">
                                        <p class="error-messages" v-if="areSame">
                                            Las horas no pueden ser iguales
                                        </p>
                                        <p class="error-messages" v-if="isGraterThanFive">
                                            La cita no puede durar más de 5 horas
                                        </p>
                                    </div>
                                </div>
                            </b-col>
                        </b-row>
                    </div>
                    <div>
                        <b-row class="mt-4">
                            <b-col cols="12" md="6" lg="6">
                                <div class="field">
                                    <span class="p-float-label p-input-icon-right">
                                        <i class="pi pi-user" />
                                        <InputText class="readonly-field" id="patient-field" v-model="patientInformation.fullname" readonly/>
                                        <label for="space">Paciente:</label>
                                    </span>
                                </div>
                            </b-col>
                            <b-col cols="12" md="6" lg="6">
                                <div class="field">
                                    <span class="p-float-label p-input-icon-right">
                                        <i class="pi pi-phone"></i>
                                        <InputText class="readonly-field" id="patient-phone-field" v-model="patientInformation.phone" readonly/>
                                        <label for="patient-phone-field">Teléfono:</label>
                                    </span>
                                </div>
                            </b-col>
                        </b-row>
                        <b-row class="mt-3">
                            <b-col cols="12" lg="12">
                                <div class="field mt-3">
                                    <span class="p-float-label p-input-icon-right">
                                        <Textarea class="readonly-field" id="patient-description-field" v-model="patientInformation.studies_description" rows="2" readonly/>
                                        <label for="patient-description-field">Descripción de estudios:</label>
                                    </span>
                                </div>
                            </b-col>
                        </b-row>
                    </div>
                </div>
                <template #footer>
                    <b-row class="mt-1 justify-content-end mt-3">
                        <b-col cols="12">
                            <Button icon="pi pi-times" @click="closeModal()" label="Cancelar"
                                class="p-button-rounded p-button-secondary" />
                            <Button icon="pi pi pi-check" label="Confirmar" class="p-button-rounded button-style"
                                @click="saveChanges()" :disabled="!isFormValid && !optionSelected" :loading="onSave"
                                v-if="isEditable()"
                            />
                        </b-col>
                    </b-row>
                </template>
            </Dialog>
            <Toast />
        </b-col>
    </b-row>
</template>


<script>
import Toast from 'primevue/toast';
import Dialog from 'primevue/dialog';
import Textarea from "primevue/textarea"
import Menu from 'primevue/menu';
import Calendar from 'primevue/calendar';
import moment from 'moment'
import { formatTime } from '@/utils/regex';
import Dropdown from 'primevue/dropdown';
import InputText from "primevue/inputtext"
import Chip from 'primevue/chip/Chip';
import ConfirmPopup from 'primevue/confirmpopup';
import appointServices from '../services/appoint-services';
import { decrypt, encrypt } from '@/config/security';
import { onError,  onQuestion, onSuccess} from '@/kernel/alerts';
import Divider from 'primevue/divider';
export default {
    name: 'ModalDetailAppoint',
    components: {
        Dialog,
        Textarea,
        Toast,
        Menu,
        Calendar,
        InputText,
        Dropdown,
        Chip,
        ConfirmPopup,
        Divider
    },
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        appoint: {
            required: true
        }
    },
    data(){
        return {
            appointInfo: {
                created_at: 0,
                endHour: 0,
                id: 0,
                startHour: 0,
                space: {
                    description: '',
                    id: 0,
                    name: '',
                },
                startHour: 0,
                status: '',
                date: null
            },
            areSame: false,
            isBeforeEnd: false,
            isFormValid: false,
            isBeforeCurrent: false,
            isGraterThanFive: false,
            onSave: false,
            formChanged: false,
            options: [
                {name: 'Reagendar cita'},
                {name: 'Cancelar cita'},
                {name: 'Completar cita'},
            ],
            optionSelected: null,
            patientInformation: {
                fullname: '',
                birthdate: '',
                phone: '',
                studies_description: '',
                recommendation: '',
                support_home: '',
            },
        }
    },
    methods: {
        closeModal() {
            this.areSame = false,
            this.isBeforeEnd = false,
            this.isFormValid = false,
            this.isBeforeCurrent = false,
            this.isGraterThanFive = false,
            this.onSave = false,
            this.formChanged = false
            this.optionSelected = null
            this.$emit('update:visible', false);
        },
        formatHour(hour){
            return moment(hour).format('hh:mm A')
        },
        validateHours() {
            this.formChanged = true;
            const startTime = moment(this.appointInfo.startHour, formatTime)
            const endTime = moment(this.appointInfo.endHour, formatTime)
            const currentHour = moment()
            const date = moment(this.appointInfo.startHour)

            this.areSame = false;
            this.isBeforeEnd = false;
            this.isBeforeCurrent = false;
            this.isBeforeToday = false;
            this.isGraterThanFive = false;

            if (date.isSame(currentHour, 'day')) {
                if(currentHour.isAfter(startTime)){
                    this.isBeforeCurrent = true;
                }
            }
            if (startTime.isSame(endTime)) {
                this.areSame = true;
            }
            if (startTime.isAfter(endTime)) {
                this.isBeforeEnd = true;
            }
            if( startTime.isBefore(moment())){
                this.isBeforeToday = true;
            }
            if(endTime.diff(startTime, 'hours') > 5){
                this.isGraterThanFive = true;
            }
            this.isFormValid = !this.areSame && !this.isBeforeEnd && !this.isBeforeCurrent && !this.isGraterThanFive;
        },
        getDate(date){
            return moment(date).format('DD/MM/YYYY')
        },
        async saveChanges(){
            if(this.optionSelected){
                try {
                    this.onSave = true
                    switch (this.optionSelected) {
                        case 'Reagendar cita':
                            if(this.formChanged){
                                if(this.isFormValid){
                                    this.rescheduleAppoint()
                                    this.$emit("onSpaceSelected")
                                }else{
                                    this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail:'Verifica los campos', life: 3000});
                                }
                            }else{
                                this.$toast.add({severity:'info', summary: '¡Vaya!', detail:'No se han realizado cambios en las horas', life: 3000});
                            }
                            break;
                        case 'Cancelar cita':
                            this.canclAppoint()
                            this.$emit("onSpaceSelected")
                            break;
                        case 'Completar cita':
                            this.compltAppoint()
                            this.$emit("onSpaceSelected")
                            break;
                        default:
                            this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail:'Selecciona una opción válida', life: 3000});
                            break;
                    }
                } catch (error) {
                    this.$toast.add({severity:'error', summary: '¡Error!', detail:'Ocurrió un error al procesar la solicitud', life: 3000});
                }
            }else{
                this.$toast.add({severity:'warn', summary: '¡Cuidado!', detail:'Debe seleccionar una opción', life: 3000});
            }
        },
        setIconByStatus(status){
            let icon = ''
            switch (status) {
                case 'Pendiente':
                    icon = 'pi pi-clock'
                    break;
                case 'Completada':
                    icon = 'pi pi-check'
                    break;
                case 'Cancelada':
                    icon = 'pi pi-times'
                    break;
                default:
                    icon = 'pi pi-question'
                    break;
            }
            return icon
        },
        setColorByStatus(status){
            let color = ''
            switch (status) {
                case 'Pendiente':
                    color = 'orange'
                    break;
                case 'Completada':
                    color = '#368368'
                    break;
                case 'Cancelada':
                    color = 'gray'
                    break;
                case 'Reprogramada':
                    color = '#2196F3'
                    break;
                default:
                    color = 'red'
                    break;
            }
            return color
        },
        async compltAppoint(){
            if (await onQuestion('¿Estás seguro de completar la cita?')) {
                const encrypted = await encrypt(JSON.stringify({id: this.appointInfo.id}))

                const { status, data } = await appointServices.completeAppoint(encrypted)
                if (status === 400) {
                    this.onSave = false
                    let message = 'Ocurrion un error al completar la cita'
                    switch (data.text) {
                        case 'User not found':
                            message = 'Usuario no encontrado'
                            break;
                        case 'Not found':
                            message = 'Cita no encontrada'
                            break;
                        case 'Treatment not found':
                            message = 'Tratamiento no encontrado'
                            break;
                        case 'Expedient not found':
                            message = 'Expediente no encontrado'
                            break;
                        case 'Unauthorized user':
                            message = 'Usuario no autorizado'
                            break;
                    }
                    await onError('Ocurrió un error', message).then(() => this.closeModal())
                }
                if (status === 200) {
                    this.onSave = false
                    await onSuccess('Cita completada', 'La cita ha sido completada con éxito')
                        .then(() => this.closeModal())
                }
            }
        },
        async canclAppoint(){
            try {
                if (await onQuestion('¿Estás seguro de cancelar la cita?')) {
                    this.onSave = true
                    const encrypted = await encrypt(JSON.stringify({id: this.appointInfo.id}))
                    const { status, data } = await appointServices.cancelAppoint(encrypted)
                    if (status === 400) {
                        this.onSave = false
                        let message = 'Ocurrion un error al cancelar la cita'
                        switch (data.text) {
                            case 'User not found':
                                message = 'Usuario no encontrado'
                                break;
                            case 'Not found':
                                message = 'Cita no encontrada'
                                break;
                            case 'Treatment not found':
                                message = 'Tratamiento no encontrado'
                                break;
                            case 'Expedient not found':
                                message = 'Expediente no encontrado'
                                break;
                            case 'Unauthorized user':
                                message = 'Usuario no autorizado'
                                break;
                        }
                        await onError('Ocurrió un error', message).then(() => this.closeModal())
                    }
                    if (status === 200) {
                        this.onSave = false
                        await onSuccess('Cita cancelada', 'La cita ha sido cancelada con éxito')
                            .then(() => this.closeModal())
                    }
                }
            } catch (error) {}
        },
        async rescheduleAppoint(){
           try {
               if (await onQuestion('¿Estás seguro de reprogramar la cita?')) {
                   this.onSave = true
                   const { startHour, endHour, id } = this.appointInfo
                   const rescheduledAppoint = {
                       id,
                       start_hour: this.formmatDate(startHour),
                       end_hour: this.formmatDate(endHour)
                   }
                   const encrypted = await encrypt(JSON.stringify(rescheduledAppoint))
                   const { status, data } = await appointServices.reschedule(encrypted)
                   if (status === 400) {
                       this.onSave = false
                       let message = 'Ocurrion un error al reprogramar la cita'
                       switch (data.text) {
                           case 'User not found':
                               message = 'Usuario no encontrado'
                               break;
                           case 'Not found':
                               message = 'Cita no encontrada'
                               break;
                           case 'Treatment not found':
                               message = 'Tratamiento no encontrado'
                               break;
                           case 'Expedient not found':
                               message = 'Expediente no encontrado'
                               break;
                           case 'Unauthorized user':
                               message = 'Usuario no autorizado'
                               break;
                           case "Maximum 5 hours":
                               message = 'La cita no puede durar más de 5 horas'
                               break;
                           case "Minimum 1 hour":
                               message = 'La cita debe durar al menos 1 hora'
                               break;
                           case "Invalid schedule":
                               message = 'Horario no válido'
                               break;
                           case "The space is busy in this schedule":
                               message = 'El espacio está ocupado en este horario'
                               break;
                           case "Cannot be more than one month from now":
                               message = 'La cita no puede programarse después de un mes'
                               break;
                            case "Cannot update a canceled or completed appointment":
                                message = 'No se puede reprogramar una cita cancelada o completada'
                                break;
                       }
                       await onError('Ocurrió un error al reprogramar', message).then(() => this.closeModal())
                   }
                   if (status === 200) {
                       this.onSave = false
                       await onSuccess('Cita reprogramada', 'La cita ha sido reprogramada con éxito')
                           .then(() => {
                               this.$emit("onSpaceSelected")
                               this.closeModal()
                           }
                        )
                   }
               }
           } catch (error) {}
        },
        formmatDate(fomattedHour){
            const date = this.appointInfo.date.toISOString().split('T')[0]
            const hour = moment(fomattedHour).format('HH:mm:ss')
            return `${date}T${hour}`
        },
        async getAppointInfo(){
            try {
                if(this.appointInfo.id !== 0){
                    const encrypted = await encrypt(JSON.stringify(+this.appointInfo.id))
                    const { status, data: {text, result} } = await appointServices.getAppointById(encrypted)
                    if (status === 400) {
                        let message = 'Ocurrion un error al obtener la información de la cita'
                        switch (text) {
                            case 'User not found':
                                message = 'Usuario no encontrado'
                                break;
                            case 'Not found':
                                message = 'Cita no encontrada'
                                break;
                            case 'Treatment not found':
                                message = 'Tratamiento no encontrado'
                                break;
                            case 'Expedient not found':
                                message = 'Expediente no encontrado'
                                break;
                            case 'Unauthorized user':
                                message = 'Usuario no autorizado'
                                break;
                        }
                        await onError('Ocurrió un error', message).then(() => {
                            this.closeModal()
                        })
                    }
                    if (status === 200) {
                        const information = JSON.parse(await decrypt(result))
                        this.patientInformation.fullname = information.fullname
                        this.patientInformation.birthdate = this.getDate(information.birthdate)
                        this.patientInformation.phone = information.phone
                        this.patientInformation.studies_description = information.studies_description
                        this.patientInformation.recommendation = information.recommendation
                        this.patientInformation.support_home = information.support_home
                    }
                }
            } catch (error) {
                onError('Ocurrió un error', 'Ocurrió un error al obtener la información de la cita').then(() => this.closeModal())
            }
        },
        isEditable(){
            return this.appointInfo.status === 'Pendiente' || this.appointInfo.status === 'Reprogramada'
        },
        disabledDates(date) {
            return date < this.today;
        },
        setMaxDate(){
            const today = new Date()
            return new Date(today.getFullYear(), today.getMonth() + 1, today.getDate())
        }
    },
    watch:{
        appoint:{
            handler(){
                const oldAppoint = JSON.parse(this.appoint)
                this.appointInfo.space.name = oldAppoint.space.name
                this.appointInfo.created_at = oldAppoint.created_at
                this.appointInfo.endHour = new Date(oldAppoint.endHour)
                this.appointInfo.id = oldAppoint.id
                this.appointInfo.startHour = new Date(oldAppoint.startHour)
                this.appointInfo.space.description = oldAppoint.space.description
                this.appointInfo.status = oldAppoint.status
                this.appointInfo.date = new Date(oldAppoint.startHour)
                this.getAppointInfo()
            },
            deep: true
        }
    },
}
</script>

<style scoped>
.button-style{
    background: #2a715a;
    border: none;
}
.button-style:hover{
    background-color: #368368 !important;
}

.my-custom-dialog .p-dialog {
  max-width: 90%;
}

@media (max-width: 768px) {
  .my-custom-dialog .p-dialog {
    max-width: 95%;
  }
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

.form-label-required::after{
    content: " *";
    color: #ff0000;
}

.readonly-field{
    cursor: not-allowed;
}
</style>