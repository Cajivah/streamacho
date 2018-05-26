<template>
  <i
    :class="isOpen && 'caret--inverted'"
    class="caret fa fa-caret-down fa-lg"
  />
</template>

<script>
export default {
  name: 'CaretDown',
  props: {
    isOpen: {
      type: Boolean,
      required: true
    }
  }
}
</script>

<style scoped>
  .caret {
    margin-left: 5px;
    transition: 0.2s;
  }

  .caret--inverted {
    transform: rotate(180deg);
  }
</style>
