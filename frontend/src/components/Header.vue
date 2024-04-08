<template>
  <div class="head-title">
    <h4>{{ title }}</h4>
    <p class="date-right">{{ currentDate }}</p>
  </div>
</template>

<script>
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
export default {
  name: 'Header',
  props: {
    title: {
      type: String,
      required: true
    }
  },

  computed: {
    currentDate() {
      const date = new Date();
      const now = format(date, 'EEEE d \'de\' MMMM \'del\' yyyy', { locale: es });
      return now.charAt(0).toUpperCase() + now.slice(1);
    }
  }
}
</script>
<style scoped>
.head-title {
  padding: 15px 20px;
  border-radius: 5px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 8px 12px 0;
  background-color: white;
  text-align: left;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.head-title h4 {
  margin-bottom: 0;
  font-size: 24px;
  font-family: 'Roboto', 'Open Sans', sans-serif;
}

.date-right {
  margin: 0;
}
</style>