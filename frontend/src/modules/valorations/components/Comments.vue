<template>
    <div class="w-100">
        <b-row>
            <b-col cols="12" lg="4" md="6" sm="12" v-for="(feedback, index) in feedbacks" :key="index" class="mb-3">
                <div class="comment-card shadow-lg fadeclass">
                    <div>
                        <b-row>
                            <b-col cols="12" class="mb-2">
                                <Avatar icon="pi pi-user" shape="circle" size="large"/>
                            </b-col>
                            <b-col cols="12" class="mb-2">
                                <Rating v-model="feedback.score" :stars="5" :cancel="false" readonly/>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12">
                                <h6>{{ feedback.name }}</h6>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col cols="12">
                                <p>{{ feedback.comment }}</p>
                            </b-col>
                        </b-row>
                    </div>
                </div>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import Card from "primevue/card"
import Avatar from 'primevue/avatar';
import Rating from 'primevue/rating';

export default {
    name: 'Comments',
    components: {
        Card,
        Avatar,
        Rating,
    },
    props: {
        feedbacks: {
            required: true,
        }
    },
}
</script>

<style scoped>

.comment-card{
    background-color: #f5f5f5;
    border-radius: 10px;
    padding: 10px;
    margin-top: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
    width: 100%;
    transition: all 0.3s;
}

.comment-card:hover{
    transform: scale(1.05);
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