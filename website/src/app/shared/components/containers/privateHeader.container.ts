import {AfterViewInit, Component, OnInit} from '@angular/core';

export class PrivateHeaderModel {


  constructor() {

  }

}

@Component({
  selector: 'private-header',
  templateUrl: './privateHeader.container.html',
  styleUrls: ['./privateHeader.container.scss']
})
export class PrivateHeader implements OnInit, AfterViewInit {
  model: PrivateHeaderModel = new PrivateHeaderModel();

  constructor() {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

