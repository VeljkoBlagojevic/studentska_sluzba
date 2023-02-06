import './App.css';
import Navbar from './components/Navbar';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Sidebar from './components/Sidebar';
import Login from './components/Login';
import Ispiti from './components/Ispiti';
import PrijavaIspita from './components/PrijavaIspita';
import Molba from './components/Molba';
import Student from './components/Student2';
import UnosMolbe from './components/UnosMolbe';
import BiranjePredmeta from './components/BiranjePredmeta';
import MojiPredmeti from './components/MojiPredmeti';
import Obavestenja from './components/Obavestenja';
import { useState } from 'react';
import IspitiUspesno from './components/IspitiUspesno';
import IspitiNeuspesno from './components/IspitiNeuspesno';

const urlBase = "localhost:8080";

function App() {
  const [isAdmin, setAdmin] = useState(false);
  
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
              <Route path='/molba' element={<Molba/>} />
              <Route path='/student' element={<Student/>} />
              <Route path='/unesiMolbu' element={<UnosMolbe/>} />
              <Route path='/biranjePredmeta' element={<BiranjePredmeta/>} />
              <Route path='/mojiPredmeti' element={<MojiPredmeti/>} />
              <Route path='/obavestenja' element={<Obavestenja/>} />
            </Routes>
          </div>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
