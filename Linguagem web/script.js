document.getElementById('lpForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const messageDiv = document.getElementById('message');

    // Simulação de envio
    messageDiv.innerText = `Obrigado, ${nome}! Enviamos os detalhes da peneira para ${email}.`;
    messageDiv.style.color = "#28a745";

    // Limpar formulário
    this.reset();
});