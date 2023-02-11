import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
 
 
class Student extends React.Component {
    state = {
        data: [],
        student: []
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
        <div className='Student'>
            <table class="table">
                <tbody>
                    <tr>
                        <th><h1>Podaci o studentu</h1></th>
                    </tr>
                    <tr>
                        <th>Broj indeksa</th>
                        <td><input type="text" value={this.state.student?.indeks}/></td>
                    </tr>
                    <tr>
                        <th>Ime i prezime</th>
                        <td><input type="text" value={this.state.student?.ime + " " + this.state.student?.prezime}/></td>
                    </tr>
                    <tr>
                        <th>Username</th>
                        <td><input type="text" value={this.state.student?.username}/></td>
                    </tr>
                    <tr>
                        <th>Ime roditelja</th>
                        <td><input type="text" value={this.state.student?.imeRoditelja}/></td>
                    </tr>
                    <tr>
                        <th>JMBG</th>
                        <td><input type="text" value={this.state.student?.jmbg}/></td>
                    </tr>
                    <tr>
                        <th>Mesto rodjenja</th>
                        <td><input type="text" value={this.state.student?.mestoRodjenja}/></td>
                    </tr>
                    <tr>
                        <th>Licni email</th>
                        <td><input type="text" value={this.state.student?.licniEmail}/></td>
                    </tr>
                    <tr>
                        <th>Studentski email</th>
                        <td><input type="text" value={this.state.student?.studentskiEmail}/></td>
                    </tr>
                    <tr>
                        <th>Broj telefona</th>
                        <td><input type="text" value={this.state.student?.brojTelefona}/></td>
                    </tr>
                    
                </tbody>
            </table>
            
            <div class="student2">
                <img style={{width: "300px", padding: "5px"}} src={this.state.student.slika} alt="" />
            </div>
            <button type="button" class="btn btn-outline-danger" >Potvrdi</button>
        </div>
      );
  }
}
 
export default Student;