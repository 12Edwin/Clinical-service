<template>
  <div class="w-100">
    <h1>Historial clínico</h1>
    <Expedients/>
  </div>
</template>

<script>
import Expedients from "@/modules/expedient/components/Expedients.vue";

export default {
  components: {Expedients}

}
</script>

<style lang="scss" scoped>

</style>