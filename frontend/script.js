function showTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // Show selected tab
    document.getElementById(tabName + '-form').classList.add('active');
    document.querySelector(`[onclick="showTab('${tabName}')"]`).classList.add('active');
}

function showMessage(text, isError = false) {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = 'message ' + (isError ? 'error' : 'success');
    setTimeout(() => {
        messageDiv.className = 'message';
    }, 3000);
}

document.getElementById('subscription-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData.entries());
    
    try {
        const response = await fetch('http://localhost:8080/api/subscriptions', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        showMessage('Subscription created successfully!');
        e.target.reset();
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.getElementById('delete-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const id = e.target.id.value;
    
    try {
        const response = await fetch(`http://localhost:8080/api/subscriptions/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        showMessage('Subscription deleted successfully!');
        e.target.reset();
    } catch (error) {
        showMessage(error.message, true);
    }
}); 