import {SignUpForm} from '../forms/signUpForm.form';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

export class SignUpPageModel {


  constructor() {

  }

}

@Component({
  selector: 'sign-up-page',
  templateUrl: './signUpPage.page.html',
  styleUrls: ['./signUpPage.page.scss']
})
export class SignUpPage implements OnInit, AfterViewInit {
  @ViewChild(SignUpForm)
  private readonly signUpFormElement: SignUpForm;
  model: SignUpPageModel = new SignUpPageModel();

  constructor(private readonly route: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

