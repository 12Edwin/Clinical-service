<template>
  <div class="w-100 h-100">
    <Header title="Historia Clínica" style="margin-bottom: 20px"/>
    <div class="position-relative" style="min-height: 80%">
      <Expedients/>
    </div>
  </div>
</template>

<script>
import Expedients from "@/modules/expedient/components/Expedients.vue";
import Header from "@/components/Header.vue";

export default {
  components: {Header, Expedients}

}
</script>

<style lang="scss" scoped>

</style>