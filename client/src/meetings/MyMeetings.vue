<template>
  <meetings-panel :organisedOnly=true />
</template>

<script>
  import MeetingsPanel from '@/meetings/MeetingsPanel';

  export default {
    name: 'my-meetings',
    components: {
      MeetingsPanel
    }
  };
</script>

<style scoped>

</style>