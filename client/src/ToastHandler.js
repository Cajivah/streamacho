import Vue from 'vue';

export const showErrorToasts = (error) => {
  if(error && error.messages) {
    error.messages.forEach(msg => Vue.toasted.global.error_toast({message:msg}).goAway(2500));
  }
};