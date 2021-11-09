import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

export class FooterModel {


  constructor() {

  }

}

@Component({
  selector: 'footer',
  templateUrl: './footer.container.html',
  styleUrls: ['./footer.container.scss']
})
export class Footer implements OnInit, AfterViewInit {
  model: FooterModel = new FooterModel();

  constructor(private readonly router: Router) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  onAboutClick() {
    this.router.navigate(['/about']);
  }

}

