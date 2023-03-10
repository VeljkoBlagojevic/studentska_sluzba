import React from 'react';
import { Link } from 'react-router-dom';

function SidebarAdmin() {
  if (localStorage.getItem('token')!=null){
  return (
    <div className='Sidebar2'>
      <div class='Sidebar'>
  <nav id="sidebarMenu" class="collapse d-lg-block sidebar collapse bg-white">
    <div class="position-sticky">
      <div class="list-group list-group-flush mx-3 mt-4">
      <Link
          to="/obavestenja"
          class="list-group-item list-group-item-action py-2 ripple"
          aria-current="true"
        >
          <i class="fas fa-tachometer-alt fa-fw me-3"></i><span>Obavestenja</span>
        </Link>
        <Link
          to="/upisiOcenu"
          class="list-group-item list-group-item-action py-2 ripple"
          aria-current="true"
        >
          <i class="fas fa-tachometer-alt fa-fw me-3"></i><span>Prijave</span>
        </Link>
        
        <Link to="/molbeUObradi" class="list-group-item list-group-item-action py-2 ripple"
          ><i class="fas fa-chart-line fa-fw me-3"></i><span>Molba</span></Link>
        
        <Link to="/student" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Podaci o studentu</span></Link>

        <Link to="/izmeniPodatke" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Izmeni podatke o studentu</span></Link>
        
        <Link to="/oNama" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>O Fakultetu</span></Link>
        
        <Link to="/admin" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>IP adresa</span></Link>

        <Link to="/krajRada" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Kraj rada</span></Link>
        
      </div>
    </div>
    
  </nav>
  </div>
    </div>
  );
  }
}

export default SidebarAdmin