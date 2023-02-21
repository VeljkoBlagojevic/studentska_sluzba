import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
 
 
class Student extends React.Component {
    state = {
        data: [],
        student: [],
        poruka: ''
    }

    handleForceupdateMethod() {
        this.forceUpdate();
    };

    potvrdi(id){
        axios({
            method: 'patch',
            url: '/api/v1/studenti/'+id,
            baseURL: 'http://localhost:8080',
            data: {
                ime: document.getElementById("ime").value,
                prezime: document.getElementById("prezime").value,
                jmbg: document.getElementById("jmbg").value,
                licniEmail: document.getElementById("licniEmail").value,
                imeRoditelja: document.getElementById("imeRoditelja").value,
                brojTelefona: document.getElementById("brojTelefona").value
            },
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.state.poruka = 'Podaci su izmenjeni';
            this.handleForceupdateMethod();
        }, (error) => {
            console.log(error);
        });
        //this.state.data.find(el => el.id === localStorage.getItem('student'));
    }
    

    componentDidMount() {
        axios({
            method: 'get',
            url: '/api/v1/studenti',
            baseURL: 'http://localhost:8080',
            data: {},
            headers: { 'Authorization': 'Bearer '+localStorage.getItem('token')}
          }).then((response) => {
            console.log(response);
            this.setState({data: response.data});
            //alert(localStorage.getItem('student'));
            //alert(response.data.filter(el => el.id === localStorage.getItem('student'))[0]);
            this.setState({student: response.data.find(el => '' + el.id === localStorage.getItem('student'))});
            //alert(response.data[0].id);
            //alert(JSON.stringify(response.data.mestoRodjenja.naziv));
            
        }, (error) => {
            console.log(error);
        });
        //this.state.data.find(el => el.id === localStorage.getItem('student'));
    }

  render() {
    return (
        <div>
        <div className='Student'>
            <table class="table">
                <tbody>
                    <tr>
                        <th><h1>Podaci o studentu</h1></th>
                    </tr>
                    <tr>
                        <th>Ime</th>
                        <td><input type="text" id= "ime" defaultValue={this.state.student?.ime}/></td>
                    </tr>
                    <tr>
                        <th>Prezime</th>
                        <td><input type="text" id= "prezime" defaultValue={this.state.student?.prezime}/></td>
                    </tr>
                    <tr>
                        <th>Ime roditelja</th>
                        <td><input type="text" id= "imeRoditelja" defaultValue={this.state.student?.imeRoditelja}/></td>
                    </tr>
                    <tr>
                        <th>JMBG</th>
                        <td><input type="text" id= "jmbg" defaultValue={this.state.student?.jmbg}/></td>
                    </tr>
                    <tr>
                        <th>Licni email</th>
                        <td><input type="text" id= "licniEmail" defaultValue={this.state.student?.licniEmail}/></td>
                    </tr>
                    <tr>
                        <th>Broj telefona</th>
                        <td><input type="text" id= "brojTelefona" defaultValue={this.state.student?.brojTelefona}/></td>
                    </tr>
                    
                </tbody>
            </table>
            
            <div class="student2">
                <img style={{width: "300px", padding: "5px"}} src={this.state.student.slika} alt="" />
            </div>
            <button type="button" class="btn btn-outline-danger" onClick={() =>this.potvrdi(this.state.student?.id)}>Potvrdi</button>
        </div>
        <div className="poruka">
        <h4><b>{this.state.poruka}</b></h4>
      </div>
      </div>
      );
  }
}
 
export default Student;