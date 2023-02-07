import React from 'react';
import { Link } from 'react-router-dom';

function Sidebar() {
  return (
    <div className='Sidebar2'>
      <div class='Sidebar'>
  <nav id="sidebarMenu" class="collapse d-lg-block sidebar collapse bg-white">
    <div class="position-sticky">
      <div class="list-group list-group-flush mx-3 mt-4">
      <a
          href="/obavestenja"
          class="list-group-item list-group-item-action py-2 ripple"
          aria-current="true"
        >
          <i class="fas fa-tachometer-alt fa-fw me-3"></i><span>Obavestenja</span>
        </a>
        <a
          href="/prijavaIspita"
          class="list-group-item list-group-item-action py-2 ripple"
          aria-current="true"
        >
          <i class="fas fa-tachometer-alt fa-fw me-3"></i><span>Prijava ispita</span>
        </a>
        <a href="/biranjePredmeta" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-area fa-fw me-3"></i><span>Biranje predmeta</span>
        </a>
        <a href="/ispitiUspesno" class="list-group-item list-group-item-action py-2 ripple"
          ><i class="fas fa-lock fa-fw me-3"></i><span>Uspesna polaganja</span></a
        >
        <a href="/ispitiNeuspesno" class="list-group-item list-group-item-action py-2 ripple"
          ><i class="fas fa-lock fa-fw me-3"></i><span>Neuspesna polaganja</span></a
        >
        <a href="/molbeUObradi" class="list-group-item list-group-item-action py-2 ripple"
          ><i class="fas fa-chart-line fa-fw me-3"></i><span>Molba</span></a
        >
        <a href="/mojiPredmeti" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Moji predmeti</span></a
        >
        <a href="/student" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Podaci o studentu</span></a
        >
        <a href="/krajRada" class="list-group-item list-group-item-action py-2 ripple">
          <i class="fas fa-chart-pie fa-fw me-3"></i><span>Kraj rada</span></a
        >
        
      </div>
    </div>
    
  </nav>
  </div>
    </div>
  );
}

export default Sidebar