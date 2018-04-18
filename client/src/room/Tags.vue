<template>
  <div class="tags">
    <span class="tag is-primary mr-1" v-for='tag in tags'>{{tag}}</span>
  </div>
</template>

<script>
  export default {
    name: 'Tags',
    props: ['tags']
  }
</script>

<style scoped>

</style>