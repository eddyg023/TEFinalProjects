<template>
    <article v-if="coach" class="coach-profile">
        <header>
            <h1>{{ coach.name }}</h1>
        </header>
        <button v-if="!showForm && $store.state.token != null" @click="showForm = true" class="updateButton">Update Coach</button>
        <button v-if="$store.state.token != null" @click="deleteCoach" class="deleteButton">Delete Coach</button>
        <section>
            <section class="form-section" v-if="showForm && $store.state.token != null">
                <form @submit.prevent="updateCoach">
                    <fieldset class="form-fieldset">
                        <legend>Update Coach</legend>
                        <label>
                            <span>Coach Name:</span>
                            <input type="text" v-model="newCoachName" placeholder="Enter coach name" >
                        </label>
                        <label>
                            <span>Coach Age:</span>
                            <input type="number" v-model="newCoachAge" placeholder="Enter coach age" >
                        </label>
                        <label>
                            <span>Years Coached:</span>
                            <input type="number" v-model="newCoachYearsCoached" placeholder="Enter years coached" >
                        </label>
                        <button type="submit" class="btn btn-primary" @click="updateCoach">Update Coach</button>
                        <button type="button" class="btn btn-secondary" @click="cancelForm">Cancel</button>
                    </fieldset>
                </form>
            </section>

            <img src="/images/profileheader.jpg" class="profilepicture"/>

                <p><span>Age:</span> {{ coach.age }} years old</p>
                <p><span>Years Coached:</span> {{ coach.years_coached }}</p>
                <p><span class="teamdets" @click="showTeam(team.teamId)">Team: {{ coach.teamId }}</span> </p>

        </section>
        <button @click="$router.go(-1)">Back to Previous</button>
    </article>
</template>

<script>
    import coachService from '@/services/CoachService';
    import teamService from '@/services/TeamService';

    export default {
        data(){
            return {
                coach: null,
                teams: '',
                showForm: false,
                newCoachName: '',
                newCoachAge: '',
                newCoachYearsCoached: '',
            };
        },
        created(){
            coachService.getCoach(this.$route.params.id).then(response => {
                    this.coach = response.data;
                    teamService.getTeam(this.coach.teamId).then(response => {
                    this.teamName = result.data.name;
                    });
                }).catch(error => {
                    console.error(error);
                });
            teamService.getTeam(this.$route.params.id)
            .then(response => {
            this.team = response.data;
            }).catch(error => {
        console.error(error);
      });
        },
        methods: {
            showTeam(id){
                this.$router.push({ name: 'TeamDetail', params: {id: id} });
                },
            cancelForm() {
                this.showForm = false;
                this.newCoachName = '';
                this.newCoachAge = '';
                this.newCoachYearsCoached = '';
            },
            updateCoach() {
                const updatedCoach = {
                    name: this.newCoachName,
                    age: this.newCoachAge,
                    years_coached: this.newCoachYearsCoached,
                    teamId: this.coach.teamId
                };

                coachService.updateCoach(this.$route.params.id, updatedCoach)
                    .then(response => {
                        this.coach = response.data;
                        this.showForm = false;
                    }).catch(error => {
                        console.error(error);
                    });
            },
            deleteCoach() {
                coachService.deleteCoach(this.$route.params.id)
                    .then(response => {
                        this.$router.push({name: 'Coach'});
                    }).catch(error => {
                        console.error(error);
                    });
            }
        }
    };
</script>

<style scoped>
    .coach-profile {
        max-width: 600px;
        margin: 40px auto;
        padding: 20px;
        background-color: #f9f9f9;
        border: 1px solid #ddd;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
    }

    header {
        background-color: #337ab7;
        color: #fff;
        padding: 10px;
        border-radius: 10px 10px 0 0;
    }

    .profilepicture {
        width: 200px;
        height: 300px;
        object-fit: cover;
        border-radius: 100px;
        display: block;
        margin: 0 auto;
        margin-top: 20px;
    }

    h1 {
        text-align: center;
        font-size: 24px;
        margin-bottom: 10px;
    }

    section {
        padding: 20px;
    }

    p {
        margin-bottom: 10px;
    }

    span {
        font-weight: bold;
        margin-right: 10px;
    }
    
    button{
    background-color: #337ab7;
    color: #fff;
    font-size: small;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }

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

    .teamdets:hover {
        cursor: pointer;
        color: rgb(15, 15, 238);
    }

    .updateButton{
    margin-top: 10px;
    margin-right: 5px;
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

        input[type="text"], input[type="number"] {
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

        input[type="text"], input[type="number"] {
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
        