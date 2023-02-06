import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class MojiPredmeti extends React.Component {

    state = {
      data: []
    }
    
    componentDidMount() {
      axios({
          method: 'get',
          url: '/api/v1/predmeti/slusa1',
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
      
    render(){
  return (
    <div className='MojiPredmeti'>
        <h1>Moji predmeti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                </tr>
            </thead>
            <tbody>
                {this.state?.data.map((el, index) => <tr><td>{index+1}</td><td>{el.naziv}</td><td>{el.espb}</td><td></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}}

export default MojiPredmeti