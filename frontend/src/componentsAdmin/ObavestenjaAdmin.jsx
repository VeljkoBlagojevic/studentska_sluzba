import React from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

class ObavestenjaAdmin extends React.Component {

  state = {
    data: []
  }
  
  componentDidMount() {
    axios({
        method: 'get',
        url: '/api/v1/obavestenja',
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
    <div className='Obavestenja'>
        <h1>Obavestenja</h1> 

        {this.state?.data.map(el => 
          <div class="post">
            <h3>{el.datum}</h3>
            <p>{el.sadrzaj}</p>
          </div>
        )}
        <br></br>
        <button type="button" class="btn btn-outline-danger" onClick={() => window.location.href = "/unosObavestenja"}>Unesi obavestenje</button>
    </div>
  );
}}

export default ObavestenjaAdmin