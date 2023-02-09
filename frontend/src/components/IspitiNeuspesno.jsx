import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class IspitiNeuspesno extends React.Component {
    state = {
      data: []
    }
    
    componentDidMount() {
      axios({
          method: 'get',
          url: '/api/v1/polaganja/neuspesna',
          baseURL: 'http://localhost:8080',
          data: {},
          headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
        }).then((response) => {
          console.log(response);
          this.setState({data: response.data});
          console.log(this.state.data);
        }, (error) => {
          console.log(error);
        });
    }
    
    render(){
  return (
    <div className='Ispiti'>
        <h1>Ispiti</h1>
        <table class="table">
            <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Predmet</th>
                    <th scope="col">ESPB</th>
                    <th scope="col">Ocena</th>
                    <th scope="col">Datum</th>
                </tr>
            </thead>
            <tbody>
                    {this.state.data.map((el, index) => <tr><td>{index+1}</td><td>{el.predmet?.naziv}</td><td>{el.predmet?.ESPB}</td><td>{el.ocena}</td><td>{el.datum}</td><td></td></tr>)}
            </tbody>
        </table>
    </div>
  );
}
}

export default IspitiNeuspesno