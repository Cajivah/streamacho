<template>
  <form @submit.prevent="onSubmit()">
    <input
      class="form-control"
      name="meeting"
      placeholder="meeting"
      v-model="newMeeting.name"
      v-validate="'required|min:3'"
    >
    <input
      class="form-control"
      name="date"
      placeholder="date"
      v-model="newMeeting.date"
      v-validate="'required|date_format:dd-MM-yyyy'"
    >
    <button class="btn btn-primary">Add</button>
    <div class="alert alert-danger" v-show="errors.has('meeting') | errors.has('date')">
      {{ errors.first('meeting') }}
      {{ errors.first('date') }}
    </div>
  </form>
</template>

<script>
  import uuid from 'uuid/v4';

  export default {
    name: "AddMeeting",
    data() {
      return {
        newMeeting: {
          name: '',
          date: new Date()
        }
      }
    },
    methods: {
      onSubmit() {
        this.$validator.validateAll().then(result => {
          if (!result) {
            return;
          }
          this.$emit('onAddMeeting', {
            id: uuid(),
            ...this.newMeeting
          });
          this.newMeeting.name = '';
          this.newMeeting.date = new Date();
          this.$validator.reset();
        });
      },
      formatDate(date) {
        
      }
    }
  }
</script>

<style scoped>

</style>
