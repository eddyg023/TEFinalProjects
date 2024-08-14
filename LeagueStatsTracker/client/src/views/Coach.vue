<template>
  <div class="container">
    <header>
      <h1>Coaches</h1>
    </header>
    <button v-if="!showForm && $store.state.token != null" @click="showForm = true">Add New Coach</button>
    <section class="form-section" v-if="showForm && $store.state.token != null">
      <form @submit.prevent="addCoach">
        <fieldset class="form-fieldset">
          <legend>Add New Coach</legend>
          <label>
            <span>Coach Name:</span>
            <input type="text" v-model="newCoachName" placeholder="Enter Coach Name" required>
          </label>
          <label>
            <span>Coach Age: </span>
            <input type="number" v-model="newCoachAge" placeholder="Enter Coach Age" required>
          </label>
          <label>
            <span>Years Coached: </span>
            <input type="number" v-model="newCoachYears" placeholder="Enter Years Coached" required>
          </label>
          <button type="submit" class="btn btn-primary">Add Coach</button>
          <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
        </fieldset>
      </form>
    </section>
    <section class="coach-list">
      <article v-for="coach in coaches" :key="coach.id" @click="showCoach(coach.coachId)">
        <h2>{{ coach.name }}</h2>
        <p>{{ coach.teamId }}</p>
      </article>
    </section>
  </div>
</template>

<script>
import coachService from '@/services/CoachService';

export default {
  data(){
    return {
      coaches: [],
      showForm: false,
      newCoachName: '',
      newCoachAge: '',
      newCoachYears: ''
    };
  },
  created(){
    coachService.getCoaches()
      .then(response => {
        this.coaches = response.data;
      }).catch(error => {
        console.error(error);
      });
  },
  methods: {
    showCoach(id){
      this.$router.push({name: 'CoachDetail', params: {id: id}});
    },
    addCoach(){
      const newCoach = {
        name: this.newCoachName,
        age: this.newCoachAge,
        yearsCoached: this.newCoachYearsCoached,
        teamId: 1
      };

      coachService.addCoach(newCoach)
        .then(response => {
          this.newCoachName = '';
          this.newCoachAge = '';
          this.newCoachYearsCoached = '';
          this.loadCoaches();
          this.coaches.push(response.data);
          this.showForm = false;
        }).catch(error => {
          console.error(error);
        });
    },
    loadCoaches(){
      coachService.getCoaches()
        .then(response => {
          this.coaches = response.data;
        }).catch(error => {
          console.error(error);
        });
    },
    cancelForm() {
      this.showForm = false;
      this.newCoachName = '';
      this.newCoachAge = '';
      this.newCoachYearsCoached = '';
    }
  }
};
</script>

<style scoped>
  header {
    background-color: #337ab7;
    padding: 20px;
    margin-bottom: 10px;
    border-bottom: 1px solid #ddd;
    border-radius: 10px 10px 0 0;
    text-align: center;
  }

  header h1 {
    color: #fff;
    font-size: 24px;
    margin: 0;
  }

  /* Form styles */
  .form-section {
    padding: 20px;
  }
  
  .form-fieldset legend {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  label {
    display: block;
    margin-bottom: 10px;
  }

  label span {
    display: block;
    margin-bottom: 5px;
  }

  input[type="text"], input[type="number"] {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 100%;
    margin-bottom: 10px;
  }

  button[type="submit"] {
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    margin: 10px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  button:hover {
    background-color: #23527c;
  }

  button{
    background-color: #337ab7;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

  /* Coach list styles */
  .coach-list {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 20px;
  }

  article {
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    margin: 10px;
    cursor: pointer;
    width: calc(33.33% - 20px);
    text-align: center;
  }
    article:hover {
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  article h2 {
    font-size: 18px;
    margin: 10px;
  }

  @media (max-width: 1024px) {
    nav {
      text-align: center;
    }

    .form-fieldset {
      flex-direction: column;
    }

    label {
      width: 100%;
    }

    input[type="text"], input[type="number"], select {
      width: 100%;
    }

    .coach-list {
      flex-direction: column;
    }

    article {
      width: 100%;
      margin: 10px 0;
    }
  }

  @media (max-width: 425px) {
    nav {
      text-align: center;
    }
    .form-fieldset {
      padding: 10px;
    }

    label {
      margin-bottom: 5px;
    }

    input[type="text"], input[type="number"], select {
      padding: 5px;
      font-size: 14px;
    }

    button.btn.btn-primary {
      width: auto;
      padding: 10px 20px;
      
    }

    button[type="submit"] {
      padding: 5px 10px;
      font-size: 14px;
    }

    .coach-list {
      padding: 10px;
    }

    article {
      padding: 5px;
      margin: 5px 0;
    }

    article h2 {
      font-size: 16px;
    }

    article p {
      font-size: 14px;
    }
  }
</style>