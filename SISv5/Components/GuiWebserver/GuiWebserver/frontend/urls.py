from django.urls import path, include
from . import views
from django.contrib.auth import views as auth_views

urlpatterns = [
    path('', views.index ),
    path('accounts/login/', auth_views.LoginView.as_view(template_name='frontend/login.html')),
    path('accounts/', include('django.contrib.auth.urls')),
    path('submitPoster/', views.get_vote),
    path('initTallyTable/', views.init_tally),
    path('showResults/', views.show_results),
    path('killVoting/', views.kill_voting),
]