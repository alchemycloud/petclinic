import {SessionService} from '../../../services/session.service';
import {SignInForm} from '../forms/signInForm.form';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

export class SignInPageModel {


  constructor() {

  }

}

@Component({
  selector: 'sign-in-page',
  templateUrl: './signInPage.page.html',
  styleUrls: ['./signInPage.page.scss']
})
export class SignInPage implements OnInit, AfterViewInit {
  @ViewChild(SignInForm)
  private readonly signInFormElement: SignInForm;
  model: SignInPageModel = new SignInPageModel();

  constructor(private readonly route: ActivatedRoute, private readonly sessionService: SessionService, private readonly router: Router) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

