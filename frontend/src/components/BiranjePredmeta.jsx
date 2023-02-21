import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class BiranjePredmeta extends React.Component {

    state = {
      data: []
    }

    builder(){
      this.forceUpdateHandler = this.forceUpdateHandler.bind(this);
    };
    
    forceUpdateHandler(){
      this.forceUpdate();
    };
    
    componentDidMount() {

      axios({
          method: 'get',
          url: '/api/v1/predmeti/nepolozeni',
          baseURL: 'http://localhost:8080',
          data: {},
          headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
        }).then((response) => {
          console.log(response);
          this.setState({data: response.data})
          console.log(this.state.data);
        }, (error) => {
          console.log(error);
        });


    }

    izaberiPredmete(){
      for(let i=0;i<this.state.data.length;i++){
          axios({
            method: 'patch',
            url: '/api/v1/predmeti/slusa',
            baseURL: 'http://localhost:8080',
            data: {
              id: this.state.data[i].id,
              predmet: this.state.data[i].predmet,
              trenutnoSlusa: this.state.data[i].trenutnoSlusa
            },
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            window.location.href = '/mojiPredmeti';
          }, (error) => {
            console.log(error);
          });
      }
      
    }

    promeni(id){
      this.state.data.filter(el => el.id===id).forEach(el => el.trenutnoSlusa=!el.trenutnoSlusa);
      //this.state.data[id].trenutnoSlusa=!this.state.data[id].trenutnoSlusa;
      console.log(this.state.data);
      this.forceUpdateHandler();
    }
      
    render(){
  return (
    <div className='BiranjePredmeta'>
        <h1>Biranje predmeta</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Dodaj</th>
                </tr>
            </thead>
            <tbody>
                {this.state?.data.map((el, index) => <tr><td>{index+1}</td><td>{el.predmet.naziv}</td><td>{el.predmet.ESPB}</td><td><input className="form-check-input" type="checkbox" id={index} checked={el.trenutnoSlusa} onChange={() => this.promeni(el.id)}></input></td></tr>)}
            </tbody>
        </table>
        <button type="button" className="btn btn-danger" onClick={() => this.izaberiPredmete()}>Potvrdi</button>
    </div>
  );
}}

export default BiranjePredmeta