import {SessionService} from '../../../services/session.service';
import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

export class PublicHeaderModel {


  constructor() {

  }

}

@Component({
  selector: 'public-header',
  templateUrl: './publicHeader.container.html',
  styleUrls: ['./publicHeader.container.scss']
})
export class PublicHeader implements OnInit, AfterViewInit {
  model: PublicHeaderModel = new PublicHeaderModel();

  constructor(private readonly sessionService: SessionService, private readonly router: Router) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  onSignInButtonClick() {
    this.sessionService.clear();
    this.router.navigate(['/sign-in']);
  }

  onSignUpButtonClick() {
    this.router.navigate(['/sign-up']);
  }

}

