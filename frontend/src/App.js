import './App.css';
import Navbar from './components/Navbar';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import SidebarAdmin from './componentsAdmin/SidebarAdmin';
import Login from './components/Login';
import Ispiti from './components/Ispiti';
import PrijavaIspita from './components/PrijavaIspita';
import MolbeUObradi from './components/MolbeUObradi';
import MolbeIzdate from './components/MolbeIzdate';
import MolbeAdmin from './componentsAdmin/MolbeAdmin';
import Student from './components/Student2';
import UnosMolbe from './components/UnosMolbe';
import BiranjePredmeta from './components/BiranjePredmeta';
import MojiPredmeti from './components/MojiPredmeti';
import Obavestenja from './components/Obavestenja';
import ObavestenjaAdmin from './componentsAdmin/ObavestenjaAdmin';
import { useState } from 'react';
import IspitiUspesno from './components/IspitiUspesno';
import IspitiNeuspesno from './components/IspitiNeuspesno';
import UnosObavestenja from './componentsAdmin/UnosObavestenja';
import ONama from './components/ONama';
import Analitika from './components/Analitika';
import UpisiOcenu from './componentsAdmin/UpisiOcenu';
import IzmeniPodatke from './componentsAdmin/IzmeniPodatke';
import PodaciOStudentu from './componentsAdmin/PodaciOStudentu';
import Admin from './components/Admin';

const urlBase = "localhost:8080";

function App() {
  //OVAJ IF TREBA DA PROVERI DA LI JE U PITANJU ADMIN ILI NE!!!
  //AKO NIJE ADMIN
  if(false){
    return (
    <div class='Page'>
      <BrowserRouter className='App'>
        <div class='NavBar'><Navbar/></div>
        <div class='Main'>
          <div class='SideBar'><Sidebar/></div>
          <div class='CenterComponent'>
            <Routes>
            <Route path='/' element={<Login/>} />
              <Route path='/krajRada' element={<Login/>} />
              <Route path='/ispitiUspesno' element={<IspitiUspesno/>} />
              <Route path='/ispitiNeuspesno' element={<IspitiNeuspesno/>} />
              <Route path='/prijavaIspita' element={<PrijavaIspita/>} />
              <Route path='/molbeUObradi' element={<MolbeUObradi/>} />
              <Route path='/molbeIzdate' element={<MolbeIzdate/>} />
              <Route path='/student' element={<Student/>} />
              <Route path='/unesiMolbu' element={<UnosMolbe/>} />
              <Route path='/biranjePredmeta' element={<BiranjePredmeta/>} />
              <Route path='/mojiPredmeti' element={<MojiPredmeti/>} />
              <Route path='/obavestenja' element={<Obavestenja/>} />
              <Route path='/oNama' element = {<ONama/>}/>
              <Route path='/analitika' element={<Analitika/>}></Route>
              <Route path='/admin' element={<Admin/>}></Route>
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </div>
    );
  }
  //AKO JESTE ADMIN
  else{
    return (
      <div class='Page'>
        <BrowserRouter className='App'>
          <div class='NavBar'><Navbar/></div>
          <div class='Main'>
            <div class='SideBar'><SidebarAdmin/></div>
            <div class='CenterComponent'>
              <Routes>
              <Route path='/' element={<Login/>} />
                <Route path='/krajRada' element={<Login/>} />
                <Route path='/upisiOcenu' element={<UpisiOcenu/>} />
                <Route path='/molbeUObradi' element={<MolbeAdmin/>} />
                <Route path='/molbeIzdate' element={<MolbeIzdate/>} />
                <Route path='/student' element={<Student/>} />
                <Route path='/obavestenja' element={<ObavestenjaAdmin/>} />
                <Route path='/oNama' element = {<ONama/>}/>
                <Route path='/unosObavestenja' element={<UnosObavestenja/>} />
                <Route path='/izmeniPodatke' element={<IzmeniPodatke/>}></Route>
                <Route path='/podaciOStudentu' element={<PodaciOStudentu/>}></Route>
              </Routes>
            </div>
          </div>
        </BrowserRouter>
      </div>
      );
  }
}

export default App;
