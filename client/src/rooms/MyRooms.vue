<template>
  <rooms-panel :organisedOnly=true />
</template>

<script>
  import RoomsPanel from '@/rooms/panel/RoomsPanel';

  export default {
    name: 'my-rooms',
    components: {
      RoomsPanel
    }
  };
</script>

<style scoped>

</style>